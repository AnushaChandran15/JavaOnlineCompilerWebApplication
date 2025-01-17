package com.anushachandran1502.javacompiler.compilerapi;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.anushachandran1502.javacompiler.codecompiler.CodeCompiler;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class JavaCompiler
 */
@WebServlet("/JavaCompiler")
public class JavaCompiler extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public JavaCompiler() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hai..");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		StringBuilder requestBody = new StringBuilder();
		BufferedReader br = request.getReader();
		String line;
		while((line=br.readLine())!=null)
		{
			requestBody.append(line);
		}
		System.out.println(requestBody);
		br.close();
		String requestBodyString = requestBody.toString().replace("&gt;", ">").replace("&lt;", "<");
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			jsonObj = (JSONObject) parser.parse(requestBodyString);
			CodeCompiler compiler = new CodeCompiler();
            String filePath = getServletContext().getRealPath("/WEB-INF");
            JSONObject output = compiler.run(filePath,jsonObj);
            response.getWriter().write(output.toJSONString());
		} 
		catch (ParseException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
