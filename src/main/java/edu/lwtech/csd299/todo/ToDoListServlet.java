package edu.lwtech.csd299.todo;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.apache.log4j.*;
import freemarker.core.*;
import freemarker.template.*;

@WebServlet(name = "ToDoListServlet", urlPatterns = {"/lists"}, loadOnStartup = 0)
public class ToDoListServlet extends HttpServlet {

    private static final long serialVersionUID = 2020111122223333L;
    private static final Logger logger = Logger.getLogger(ToDoListServlet.class);

    private static final String TEMPLATE_DIR = "/WEB-INF/classes/templates";
    private static final Configuration freemarker = new Configuration(Configuration.getVersion());

    private DAO<Member> memberMemoryDao = null;
    private DAO<DemoPojo> demoMemoryDao = null;
    private DAO<ToDoList> todoListDao = null;
    private DAO<Item> itemMemoryDao = null;

    private static int currentListIndex = 0;

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
        

        todoListDao = new ToDoListMemoryDAO();
        todoListDao.insert(new ToDoList("Test list", 1));
        memberMemoryDao = new MemberMemoryDAO();
        memberMemoryDao.insert(new Member("1", "1", "Ryan", "A", "f@m.com"));
        itemMemoryDao = new ItemMemoryDAO();
        addDemoData();

        logger.warn("Initialize complete!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("IN - GET " + request.getRequestURI());
        long startTime = System.currentTimeMillis();

        String command = request.getParameter("cmd");
        if (command == null) command = "home";

        String template = "";
        Map<String, Object> model = new HashMap<>();

        //TODO: Add more URL commands to the servlet
        switch (command) {
            case "home":
                List<ToDoList> todoLists = todoListDao.getAll();
                template = "home.ftl";
                model.put("todoLists", todoLists);
                break;
            case "show":

                String idParam = request.getParameter("listId");
                int id = (idParam == null) ? 0 : Integer.parseInt(idParam);
                
                ToDoList todoList = todoListDao.getByID(id);
                List<Item> items = itemMemoryDao.getByOwnerID(id);
                model.put("todoList", todoList);
                model.put("item", items);
                template = "show.ftl";

                break;
            case "login":
                template = "login.ftl";  
            default:
                logger.debug("Unknown GET command received: " + command);

                // Send 404 error response
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e)  {
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
        
        String command = request.getParameter("cmd");
        if (command == null) command = "";

        int owner = 0;
        boolean loggedIn = false;
        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                owner = Integer.parseInt((String)session.getAttribute("owner"));
                loggedIn = true;
            } catch (NumberFormatException e) {
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
        
        switch (command) {

            case "create":
                ToDoList newList = getToDoListFromRequest(request, owner);

                if (newList == null) {
                    logger.info("Create request ignored because one or more fields were empty.");
                    message = "Your new ToDoList was not created because one or more fields were empty.<br /><a href='?cmd=home'>Home</a>";
                    break;
                }

                if (todoListDao.insert(newList) > 0)
                    message = "Your new ToDoList has been created successfully.<br /><a href='?cmd=home'>Home</a>";
                else
                    message = "There was a problem adding your list to the database.<br /><a href='?cmd=home'>Home</a>";
                break;

            case "login":
                username = request.getParameter("username");
                password = request.getParameter("password");
                
                
                member = memberMemoryDao.search("username", username);
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
                    session.setAttribute("owner", owner);

                    message = "You have been successfully logged in to your account.<br /><a href='?cmd=show'>Show Lists</a>";
                } else {
                    message = "Your password did not match what we have on file.  Please try again.<br /><a href='?cmd=login'>Log In</a>";
                }

                model.put("loggedIn", loggedIn);
                model.put("message", message);
                break;

            case "register":
                username = request.getParameter("username");
                password = request.getParameter("password");
                
                member = memberMemoryDao.search("username", username);
                if (member != null) {
                    message = "That username is already registered here. Please use a different username.<br /><a href='?cmd=login'>Log In</a>";
                    model.put("message", message);
                    break;
                }

                member = new Member(username, password, firstName, lastName, email);
                memberMemoryDao.insert(member);

                message = "Welcome to TopTopTenLists.com!  You are now a registered member. Please <a href='?cmd=login'>log in</a>.";
                model.put("message", message);
                break;
            case "signup":
                
            default:
                logger.info("Unknown POST command received: " + command);

                // Send 404 error response
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e)  {
                    logger.error("IO Error: ", e);
                }
                return;
        }

        model.put("message", message);
       
        processTemplate(response, template, model);

        long time = System.currentTimeMillis() - startTime;
        logger.info("OUT- GET " + request.getRequestURI() + " " + time + "ms");
    }
    
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


    private ToDoList getToDoListFromRequest(HttpServletRequest request, int owner) {

        String description = request.getParameter("description");
        if (description == null) return null;

        List<String> items = new ArrayList<>();
        for (int i=10; i >= 1; i--) {
            String item = request.getParameter("item" + i);
            if (item == null || item.isEmpty())
                return null;
            items.add(item);
        }
        
        return null;
        //return new ToDoList(description, items, owner);
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

    private void addDemoData() {
        logger.debug("Creating sample DemoPojos...");
        
        demoMemoryDao.insert(new DemoPojo("Item I"));
        demoMemoryDao.insert(new DemoPojo("Item II"));
        demoMemoryDao.insert(new DemoPojo("Item III"));
        
        logger.info("...items inserted");
    }

}
