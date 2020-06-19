package edu.lwtech.csd299.todo;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.apache.log4j.*;
import freemarker.core.*;
import freemarker.template.*;

@WebServlet(name = "ToDoListServlet", urlPatterns = { "/lists" }, loadOnStartup = 0)
public class ToDoListServlet extends HttpServlet {

    private static final long serialVersionUID = 2020111122223333L;
    private static final Logger logger = Logger.getLogger(ToDoListServlet.class);

    private static final String TEMPLATE_DIR = "/WEB-INF/classes/templates";
    private static final Configuration freemarker = new Configuration(Configuration.getVersion());

    private DAO<Member> memberDao = null;
    private DAO<ToDoList> todoListDao = null;
    private DAO<Item> itemDAO = null;

    private DAO<Member> memberSessionDao = null;
    private DAO<ToDoList> todoListSessionDao = null;
    private DAO<Item> itemSessionDAO = null;

    private int sessionDuration = 30 * (60 * 1000); // 30 minutes

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.warn("=========================================");
        logger.warn("  ToDoListServlet init() started");
        logger.warn("    http://localhost:8080/todo/lists");
        logger.warn("=========================================");

        logger.info("Getting real path for templateDir");
        String templateDir = config.getServletContext().getRealPath(TEMPLATE_DIR);
        logger.info("...real path is: " + templateDir);

        logger.info("Initializing Freemarker. templateDir = " + templateDir);
        try {
            freemarker.setDirectoryForTemplateLoading(new File(templateDir));
        } catch (IOException e) {
            logger.error("Template directory not found in directory: " + templateDir, e);
        }
        logger.info("Successfully Loaded Freemarker");

        String jdbc = "jdbc:mariadb://localhost:3306/todo?useSSL=false&allowPublicKeyRetrieval=true";
        // String jdbc =
        // "jdbc:mariadb://csd299.cv18zcsjzteu.us-west-2.rds.amazonaws.com:3306/topten?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "todo";
        String password = "lwtech2000";
        String driver = "org.mariadb.jdbc.Driver";

        // ======== UNCOMMENT TO USE MEMORY DAOs ========
        todoListDao = new ToDoListMemoryDAO();
        itemDAO = new ItemMemoryDAO();
        memberDao = new MemberMemoryDAO();

        // ======== UNCOMMENT TO USE SQL DAOs ========
        // todoListDao = new ToDoListSqlDAO();
        // itemDAO = new ItemSqlDAO();
        // memberDao = new MemberSqlDAO();

        memberDao.init(jdbc, user, password, driver);
        todoListDao.init(jdbc, user, password, driver);
        itemDAO.init(jdbc, user, password, driver);

        logger.warn("Initialize complete!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN - GET " + request.getRequestURI());
        long startTime = System.currentTimeMillis();

        Date now = new Date();

        String command = request.getParameter("cmd");
        if (command == null)
            command = "home";

        int memberID = 0;
        boolean loggedIn = false;
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                memberID = Integer.parseInt((String) session.getAttribute("owner"));
                loggedIn = true;
                logger.debug("-----Registered user -----");
            } catch (Exception e) {

                if (now.getTime() - session.getLastAccessedTime() > sessionDuration) {
                    session.invalidate();
                    expireSession(response);
                    return;
                    // session = request.getSession(true);
                    // session.setAttribute("owner", session.getId());
                }
                memberID = -1;
                loggedIn = false;
                logger.debug("-----Unregistered user----- ");
            }
        } else {
            // create new session
            session = request.getSession(true);
            session.setAttribute("owner", session.getId());

            memberID = -1;
            loggedIn = false;
            logger.debug("-----Session is null -----");
        }

        String template = null; // 404 will be returned if template isn't set inside the switch statement

        Map<String, Object> model = new HashMap<>();
        model.put("member", memberDao.getByID(memberID));
        model.put("loggedIn", loggedIn);

        int listID;
        List<Item> items = null;
        ToDoList todoList;
        String message;

        switch (command) {
            case "home":

                List<ToDoList> todoLists = new ArrayList<>();
                if (loggedIn) {
                    todoLists = todoListDao.getByOwnerID(memberID);
                    // todoLists = todoListDao.getAll();
                } else {
                    Object o = session.getAttribute("list");
                    if (o != null) {
                        todoList = (ToDoList) o;
                        todoLists.add(todoList);
                    }

                }

                template = "home.ftl";
                model.put("todoLists", todoLists);

                break;
            case "show":
                template = "show.ftl";

                if (loggedIn) {
                    listID = parseInt(request.getParameter("id"));
                    todoList = todoListDao.getByID(listID);
                    items = itemDAO.getByOwnerID(listID);
                } else {
                    todoList = (ToDoList) session.getAttribute("list");
                    Object o = session.getAttribute("items");
                    if (o != null) {
                        items = (List<Item>) o;
                    } else {
                        items = new ArrayList<Item>();
                    }

                }

                model.put("items", items);
                model.put("todoList", todoList);
                break;

            case "delete-item":
                template = "edit.ftl";
                int itemID = parseInt(request.getParameter("itemId"));

                if (loggedIn) {
                    itemDAO.delete(itemID);

                    listID = parseInt(request.getParameter("listId"));
                    todoList = todoListDao.getByID(listID);
                    items = itemDAO.getByOwnerID(listID);
                } else {
                    todoList = (ToDoList) session.getAttribute("list");
                    items = (List<Item>) session.getAttribute("items");
                    items.remove(itemID);
                    session.setAttribute("items", items);
                }

                model.put("items", items);
                model.put("todoList", todoList);
                model.put("count", items.size());
                break;

            case "edit":
                template = "edit.ftl";

                if (loggedIn) {
                    listID = parseInt(request.getParameter("id"));
                    todoList = todoListDao.getByID(listID);
                    items = itemDAO.getByOwnerID(listID);
                } else {
                    todoList = (ToDoList) session.getAttribute("list");
                    Object o = session.getAttribute("items");
                    if (o != null) {
                        items = (List<Item>) o;
                    } else {
                        items = new ArrayList<Item>();
                    }

                }

                model.put("items", items);
                model.put("todoList", todoList);
                model.put("count", items.size());
                break;

            case "create":
                template = "edit.ftl";
                model.put("items", new ArrayList<Item>());
                model.put("count", 0);
                break;

            case "delete":
                message = "";
                template = "confirm.ftl";

                if (loggedIn) {
                    listID = parseInt(request.getParameter("id"));
                    // delete items of the to-do list first
                    items = itemDAO.getByOwnerID(listID);
                    for (Item item : items) {
                        itemDAO.delete(item.getId());
                    }
                    todoListDao.delete(listID);
                } else {
                    session.setAttribute("list", null);
                    session.setAttribute("items", null);
                }

                message = "You have successfully deleted the to-do list.<br /><a href='?cmd=home'>Home</a>";
                model.put("loggedIn", loggedIn);
                model.put("message", message);
                break;

            case "login":
                template = "login.ftl";
                break;

            case "logout":
                if (session != null) {
                    session.invalidate();
                }
                template = "confirm.ftl";
                model.put("message", "You have been successfully logged out.<br /><a href='?cmd=home'>Home</a>");
                break;

            case "signup":
                if (session != null) {
                    session.invalidate();
                }
                template = "signup.ftl";
                break;

            default:
                logger.debug("Unknown GET command received: " + command);

                // Send 404 error response
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e) {
                    logger.error("IO Error: ", e);
                }
                return;
        }
        processTemplate(response, template, model);

        long time = System.currentTimeMillis() - startTime;
        logger.info("OUT- GET " + request.getRequestURI() + " " + time + "ms");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN -POST " + request.getRequestURI());
        long startTime = System.currentTimeMillis();

        Date now = new Date();

        String command = request.getParameter("cmd");
        if (command == null)
            command = "";

        int owner = 0;
        boolean loggedIn = false;
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                owner = Integer.parseInt((String) session.getAttribute("owner"));
                loggedIn = true;
            } catch (NumberFormatException e) {
                if (now.getTime() - session.getLastAccessedTime() > sessionDuration) {
                    session.invalidate();
                    session = request.getSession(true);
                    session.setAttribute("owner", session.getId());
                }
                owner = 0;
                loggedIn = false;
            }
        }

        String message = "";
        String template = "confirm.ftl";
        Map<String, Object> model = new HashMap<>();
        String username = "";
        String password = "";
        String firstName = "";
        String lastName = "";
        String email = "";
        Member member;
        int listID;
        ToDoList todoList = null;
        List<Item> items = null;

        switch (command) {

            case "save":
                ToDoList newList = getToDoListFromRequest(request, owner);

                if (newList == null) {
                    logger.info("Create request ignored because one or more fields were empty.");
                    message = "Your new To-do list was not created because one or more fields were empty.<br /><a href='?cmd=home'>Home</a>";
                    break;
                }

                List<Item> newItems = getItemsFromRequest(request, newList.getId());
                message = "Your new ToDo List has been created successfully.<br /><a href='?cmd=home'>Home</a>";

                if (loggedIn) {
                    listID = newList.getId();
                    if (listID == -1) {
                        listID = todoListDao.insert(newList);
                    } else {
                        todoListDao.update(newList);
                    }

                    if (listID > 0) {
                        for (Item item : newItems) {
                            if (item.getId() == -1) {
                                itemDAO.insert(item);
                            } else {
                                itemDAO.update(item);
                            }
                        }
                        message = "Your To-Do List has been saved successfully.<br /><a href='?cmd=home'>Home</a>";
                    } else {
                        message = "There was a problem adding your list to the database.<br /><a href='?cmd=home'>Home</a>";
                    }
                } else {
                    session.setAttribute("list", newList);
                    session.setAttribute("items", newItems);
                }

                break;

            case "add-item":
                template = "edit.ftl";

                Item newItem = getNewItemFromRequest(request);
                if (loggedIn) {
                    itemDAO.insert(newItem);
                    listID = newItem.getListID();
                    items = itemDAO.getByOwnerID(listID);
                    todoList = todoListDao.getByID(listID);
                } else {
                    todoList = (ToDoList) session.getAttribute("list");
                    Object o = session.getAttribute("items");
                    if (o != null) {
                        items = (List<Item>) o;
                    } else {
                        items = new ArrayList<Item>();
                    }
                    items.add(new Item(items.size(), newItem));
                }

                model.put("items", items);
                model.put("todoList", todoList);
                model.put("count", items.size());
                model.put("member", memberDao.getByID(owner));
                model.put("loggedIn", loggedIn);
                break;

            case "login":
                username = request.getParameter("username");
                password = request.getParameter("password");

                member = memberDao.search("username", username);
                if (member == null) {
                    message = "We do not have a member with that username on file. Please try again.<br /><a href='?cmd=login'>Log In</a>";
                    model.put("loggedIn", loggedIn);
                    model.put("message", message);
                    break;
                }

                if (member.getPassword().equals(password)) {
                    owner = member.getId();
                    loggedIn = true;

                    session = request.getSession(true);
                    session.setAttribute("owner", "" + owner);

                    logger.info("Put owner " + owner + " into new session");

                    message = "You have been successfully logged in to your account.<br /><a href='?cmd=home'>Show Lists</a>";
                } else {
                    message = "Your password did not match what we have on file.  Please try again.<br /><a href='?cmd=login'>Log In</a>";
                }

                model.put("loggedIn", loggedIn);
                model.put("message", message);
                break;

            case "signup":
                username = request.getParameter("username");
                password = request.getParameter("password");
                firstName = request.getParameter("firstName");
                lastName = request.getParameter("lastName");
                email = request.getParameter("email");

                member = memberDao.search("username", username);
                if (member != null) {
                    message = "That username is already registered here. Please use a different username.<br /><a href='?cmd=login'>Log In</a>";
                    model.put("message", message);
                    break;
                }

                member = new Member(username, password, firstName, lastName, email);
                memberDao.insert(member);

                message = "Welcome to ToDoLists.com!  You are now a registered member. Please <a href='?cmd=login'>log in</a>.";
                model.put("message", message);
                break;

            default:
                logger.info("Unknown POST command received: " + command);

                // Send 404 error response
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e) {
                    logger.error("IO Error: ", e);
                }
                return;
        }

        model.put("message", message);

        processTemplate(response, template, model);

        long time = System.currentTimeMillis() - startTime;
        logger.info("OUT- GET " + request.getRequestURI() + " " + time + "ms");
    }

    // TODO: doPost() goes here - if needed.

    @Override
    public void destroy() {
        logger.warn("-----------------------------------------");
        logger.warn("  ToDoListServlet destroy() completed");
        logger.warn("-----------------------------------------");
    }

    @Override
    public String getServletInfo() {
        return "mr-checkoff Servlet";
    }

    // ========================================================================

    private void processTemplate(HttpServletResponse response, String template, Map<String, Object> model) {
        logger.debug("Processing Template: " + template);

        try (PrintWriter out = response.getWriter()) {
            Template view = freemarker.getTemplate(template);
            view.process(model, out);
        } catch (TemplateException | MalformedTemplateNameException | ParseException e) {
            logger.error("Template Error: ", e);
        } catch (IOException e) {
            logger.error("IO Error: ", e);
        }
    }

    private int parseInt(String s) {
        int i = -1;
        if (s != null) {
            try {
                i = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                i = -2;
            }
        }
        return i;
    }

    private ToDoList getToDoListFromRequest(HttpServletRequest request, int owner) {

        logger.info("getToDoListFromRequest : ");

        String description = request.getParameter("description");
        int id = parseInt(request.getParameter("listID"));

        return new ToDoList(id, description, owner);
    }

    private List<Item> getItemsFromRequest(HttpServletRequest request, int listID) {

        logger.info("getItemsFromRequest : ");

        List<Item> items = new ArrayList<>();
        int size = parseInt(request.getParameter("count"));

        for (int i = 0; i < size; i++) {
            String name = request.getParameter("name" + i);
            boolean completed = "on".equals(request.getParameter("completed" + i)) ? true : false;
            int id = parseInt(request.getParameter("id" + i));

            items.add(new Item(id, name, completed, listID));
        }

        return items;
    }

    private Item getNewItemFromRequest(HttpServletRequest request) {

        logger.info("getNewItemFromRequest : ");

        int listID = parseInt(request.getParameter("listID"));
        String name = request.getParameter("newItem");

        return new Item(name, listID);
    }

    private void expireSession(HttpServletResponse response) {
        String template = "confirm.ftl";
        Map<String, Object> model = new HashMap<>();
        model.put("message", "Your session has been expired.<br /><a href='?cmd=home'>Home</a>");
        processTemplate(response, template, model);

    }

}
