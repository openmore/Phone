package com.michaelshelling;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;


public class ajaxKeyPad extends HttpServle {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String number=request.getParameter("number");
		if(number != null){
			response.setContentType("text/html");
			response.getWriter().write(generateJSONData(number));
            System.out.println(number);
		}

    }
	public String generateJSONData(String digits){
        System.out.println("inside genJSON");
        LinkedList<String> res = new LinkedList<String>();
		String[] strings = new String[] { "0", "1", "2ABC", "3DEF", "4GHI",
				"5JKL", "6MNO", "7PQRS", "8TUV", "9WXYZ" };
		if (digits.length() == 0) {
			return "";
		}
		res.add("");
		int cnt = 0;
		for (int i = 0; i < digits.length(); i++) {
			int x = digits.charAt(i) - '0';
			while (res.peek().length() == i) {
				String t = res.remove();
				for (char s : strings[x].toCharArray()) {
					res.add(t + s);
					cnt++;
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{\"Number\":[");
		String prefix = "";
		for (String str : res) {
			sb.append(prefix);
			prefix = ",";
			sb.append("\"");
			sb.append(str);
			sb.append("\"");
		}
		sb.append("],\"Count\":\"");
		sb.append(cnt);
		sb.append("\"}");
        System.out.println(sb.toString());
        return sb.toString();
		
	}

}
