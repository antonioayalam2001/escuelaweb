package com.ipn.mx.controlador;

import com.ipn.mx.modelo.DbConnection;
import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dto.CategoriaDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import java.io.File;
import java.io.IOException;
import java.nio.channels.ConnectionPendingException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CategoriasServlet", value = "/CategoriasServlet")
public class CategoriasServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");
        if (accion.equals("listaDeCategorias")) {
            listadoCategorias(request, response);
        } else {
            if (accion.equals("nuevo")) {
                nuevaCategoria(request, response);
            } else {
                if (accion.equals("actualizar")) {
                    actualizarCategoria(request, response);
                } else {
                    if (accion.equals("eliminar")) {
                        eliminarCategoria(request, response);
                    } else {
                        if (accion.equals("save")) {
                            guardarCategoria(request, response);
                        } else {
                            if (accion.equals("ver")) {
                                verCategoria(request, response);
                            } else {
                                if (accion.equals("verReporte")) {
                                    verReporte(request, response);
                                } else {
                                    if (accion.equals("graficar")) {
                                        graficar(request, response);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void graficar(HttpServletRequest request, HttpServletResponse response) {
    }

    private void verReporte(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream sOs = null;
        DbConnection db = DbConnection.getInstance();
        Connection conn = db.getConection();
        try {
            sOs = response.getOutputStream();
            File report;
            byte[] b ;

            report = new File(getServletConfig().getServletContext().getRealPath("reportes/categoria.jasper"));

            b = JasperRunManager.runReportToPdf(report.getPath(),null,conn);

            response.setContentType("application/pdf");

            response.setContentLength(b.length);
            sOs.write(b,0,b.length);
            sOs.flush();
        } catch (IOException | JRException e) {
            e.printStackTrace();
        }finally {
            try {
                sOs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void verCategoria(HttpServletRequest request, HttpServletResponse response) {
    }

    private void actualizarCategoria(HttpServletRequest request, HttpServletResponse response) {
        CategoriaDAO dao = new CategoriaDAO();
        CategoriaDTO dto = new CategoriaDTO();
        dto.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
        try {
            dto = dao.setSqlRead(dto);
            request.setAttribute("dto", dto);
            RequestDispatcher rd = request.getRequestDispatcher("/categoria/categoriaForm.jsp");
            rd.forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
        }
    }

    private void nuevaCategoria(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher rD = request.getRequestDispatcher("/categoria/categoriaForm.jsp");
        try {
            rD.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getParameter("id"));
        CategoriaDAO daoC = new CategoriaDAO();
        CategoriaDTO dtoC = new CategoriaDTO();
        dtoC.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("id")));
        try {
            dtoC = daoC.setSqlRead(dtoC);
            daoC.setSqlDelete(dtoC);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {

            listadoCategorias(request, response);

        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarCategoria(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        CategoriaDTO dto = new CategoriaDTO();
        CategoriaDAO dao = new CategoriaDAO();
        String id = request.getParameter("idCat");

        if (request.getAttribute("dto") == null && id.equals("")) {
            dto.getEntidad().setNombreCategoria(request.getParameter("categoria"));
            dto.getEntidad().setDescripcionCategoria(request.getParameter("desCategoria"));
            try {
                dao.setSqlInsert(dto);
                listadoCategorias(request, response);
            } catch (SQLException ex) {
            }
        }else {
            dto.getEntidad().setIdCategoria(Integer.parseInt(id));
            dto.getEntidad().setNombreCategoria(request.getParameter("categoria"));
            dto.getEntidad().setDescripcionCategoria(request.getParameter("desCategoria"));
            dao.setSqlUpdate(dto);
            listadoCategorias(request, response);
        }

    }

    private void listadoCategorias(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        CategoriaDAO daoC = new CategoriaDAO();
        List list = daoC.setSqlReadAll();
        String [] frases  = {"Hola","Prueba"};

        int x = 10;
        request.setAttribute("listado", list);
        request.setAttribute("frases", frases);
        request.setAttribute("numero", x);
        RequestDispatcher rD = request.getRequestDispatcher("/categoria/listaDeCategorias.jsp");
        rD.forward(request, response);

    }
}

