/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rahulthawal
 */
public class ShoppingCartServlet extends HttpServlet {

//Generating a unique value which is used as a cookie value
public static int S_ID = 1;
 
// used to store HashMaps of indiviual users
public static HashMap globalMap = new HashMap();


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        // declaring user's Hash Map
        HashMap sessionInfo = null;String sID = ""; 
        
        // method findCookie is used to determine whether browser has send any cookie named " JSESSIONID"
      
        Cookie c = findCookie(request);
        
        // if no cookies named "JSESSIONID" is recieved, means that user is visiting the site for the first time.
        if (c == null)
        {
        
            // make a unique string
            sID = makeUniqueString();
        
            // creating a HashMap where books selected by the user will be stored
            sessionInfo = new HashMap() ;
        
            globalMap.put(sID, sessionInfo);
            // create a cookie named "JSESSIONID" alongwith
            // value of sID i.e. unique string
            
            Cookie sessionCookie = new Cookie("JSESSIONID", sID);
            
            // add the cookie to the response
            response.addCookie(sessionCookie);
        
        }
        
        else {
                    // if cookie is found named "JSESSIONID"
                    // retrieve a HashMap from the globalMap against
                    // cookie value i.e. unique string which is your//sessionID
                    sessionInfo = (HashMap) globalMap.get( c.getValue() );
            }
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Shooping Cart Example</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Online Book Store</h1>");    
       
        String url ="http://localhost:49528/ShoppingCart/ShoppingCartServlet";
        // user will submit the fr om to the same servlet
        out.println(
                "<form action=" + url +">" 
                 +"<input type=checkbox name=firstCB value=first/>" 
                 +" java core servlets" 
                 +"<br>"
                 +"<input type=checkbox name=secondCB value=second/>"
                 +" java how to program" 
                 +"<br>"
                 +"<input type=checkbox name=thirdCB value=third/>"
                 +" java complete reference" 
                 +"<br>"
                 +"<input type=submit value=\"Add to Cart\" />" 
                 
        );
        out.println("<br/>");
        out.println("<h1>You have selected following books</h1>");
        out.println("<br/>");
        
        //retriving params of check boxes
        String fBook = request.getParameter("first");
        String sBook = request.getParameter("second");
        String tBook = request.getParameter("third");
        
        // if first book is selected then add it to user's HashMap i.e. sessionInfo
        if ( fBook != null && fBook.equals("first") ) 
        {
                sessionInfo.put("first", "java core servlets");
        }
        
        // if second book is selected then add it to user's HashMap i.e. sessionInfo
        if (sBook != null && sBook.equals("second"))
        {       sessionInfo.put("second", "java how to program");
        
        }
    
    
    
        // if third book is selected then add it to user's HashMap i.e. sessionInfo
        if (tBook != null && tBook.equals("third"))
        {
            sessionInfo.put("third", "java complete reference");

        }
// used to display the books currently stored in
// the user's HashMap i.e. sessionInfo
        printSessionInfo(out, sessionInfo);
        out.println("</body>");
        out.println("</html>");
        out.close();
        
       
    }
    public void printSessionInfo(PrintWriter out,HashMap sessionInfo)
    {
            String title = "";
            title= (String)sessionInfo.get("first");
            if (title != null)
            {
                    out.println("<h3> "+ title +"</h3>");
            }
            
            
            title= (String)sessionInfo.get("second");
            
            if (title != null)
            {
                    out.println("<h3> "+ title +"</h3>");
            }
            
            title= (String)sessionInfo.get("third");
            
            if (title != null)
            {
                   out.println("<h3> "+ title +"</h3>");

            }
    }    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    public static HashMap findTableStoringSessions(){return globalMap;}
// method used to find a cookie named "JSESSIONID"
    private Cookie findCookie(HttpServletRequest request) {
       
        Cookie[] cookies = request.getCookies();
        if (cookies != null) 
        {
            for(int i=0; i<cookies.length; i++) 
            {
                Cookie c = cookies[i];
                if (c.getName().equals("JSESSIONID")) // name of the cookie is JSESSIONID
                {
                     return c;
                }
            }
         }
    return null;

    
}

    private String makeUniqueString() {
         
    return "ABC" + S_ID++;
    }
}