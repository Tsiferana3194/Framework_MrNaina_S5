package com.framework.core;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class FrontServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 🔍 Récupère le chemin demandé (ex: /test-framework/assets/test.html)
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath(); // /test-framework
        String path = requestURI.substring(contextPath.length()); // /assets/test.html

        // 🧩 Journalisation pour debug
        System.out.println("➡️ FrontServlet intercepts: " + path);

        // ⚙️ Si la requête est pour une ressource statique, on la laisse passer
        if (isStaticResource(path)) {
            serveStaticResource(path, response, request);
            return;
        }

        // Sinon, traitement par le framework
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().println("✅ FrontServlet actif pour : " + path);
    }

    // 📦 Vérifie si l’URL correspond à une ressource statique
    private boolean isStaticResource(String path) {
        return path.startsWith("/assets/")
                || path.endsWith(".css")
                || path.endsWith(".js")
                || path.endsWith(".png")
                || path.endsWith(".jpg")
                || path.endsWith(".jpeg")
                || path.endsWith(".gif")
                || path.endsWith(".ico")
                || path.endsWith(".html")
                || path.endsWith(".jsp");
    }

    // 🚀 Sert la ressource depuis le dossier webapp
    private void serveStaticResource(String path, HttpServletResponse response, HttpServletRequest request)
            throws IOException {

        ServletContext context = request.getServletContext();
        String realPath = context.getRealPath(path);

        if (realPath == null) {
            System.out.println("❌ realPath null pour : " + path);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File file = new File(realPath);
        if (!file.exists() || file.isDirectory()) {
            System.out.println("❌ Fichier introuvable : " + realPath);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 🔧 Détermine le type MIME
        String mimeType = context.getMimeType(realPath);
        if (mimeType == null) mimeType = "application/octet-stream";
        response.setContentType(mimeType);
        response.setContentLengthLong(file.length());

        // 🔁 Envoie le contenu au navigateur
        try (InputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        System.out.println("✅ Fichier servi : " + realPath);
    }
}
