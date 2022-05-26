package com.ipn.mx.controlador;

import com.ipn.mx.modelo.DbConnection;
import com.ipn.mx.modelo.dao.ArticuloDAO;
import com.ipn.mx.modelo.dao.CategoriaDAO;
import com.ipn.mx.modelo.dao.DatosGraficaDAO;
import com.ipn.mx.modelo.dto.ArticuloDTO;
import com.ipn.mx.modelo.dto.CategoriaDTO;
import com.ipn.mx.modelo.dto.DatosGraficaDTO;
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "ArticuloS", value = "/ArticuloS")
public class ArticuloS extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");
        if (accion.equals("list")) {
            listaArt(request, response);
        } else {
            if (accion.equals("nue")) {
                nueArt(request, response);
            } else {
                if (accion.equals("act")) {
                    actArt(request, response);
                } else {
                    if (accion.equals("del")) {
                        delArt(request, response);
                    } else {
                        if (accion.equals("save")) {
                            saveArt(request, response);
                        } else {
                            if (accion.equals("ver")) {
                                verArt(request, response);
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

    private void graficar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
//        JFreeChart chart = ChartFactory.
//                createPieChart("Articulos por Categoria",getDataGrafica(),true,true, Locale.getDefault());
        JFreeChart chart = ChartFactory.
                createBarChart("Articulos por Categoria",
                        "Categoria"
                        ,"Articulos",
                        getDataGrafica(),
                        PlotOrientation.VERTICAL,true,true,false);
        ChartPanel panel = new ChartPanel(chart);
        String archivo = getServletConfig().getServletContext().getRealPath("/grafica.png");
        ChartUtils.saveChartAsPNG(new File(archivo),chart,800,600);
        RequestDispatcher rD = request.getRequestDispatcher("/articulo/grafica.jsp");
        rD.forward(request,response);
    }

    private DefaultCategoryDataset getDataGrafica() throws SQLException {
        DefaultCategoryDataset pie = new DefaultCategoryDataset();
        DatosGraficaDAO dao = new DatosGraficaDAO();
        List datos = dao.graficar();

        for (Object elemento: datos
             ) {
            DatosGraficaDTO dto = (DatosGraficaDTO) elemento;
            pie.setValue(dto.getCantidad(), dto.getNombre(), dto.getNombre());
        }
        return pie;
    }

    private void verReporte(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream sOs = null;
        DbConnection db = DbConnection.getInstance();
        Connection conn = db.getConection();
        try {
            sOs = response.getOutputStream();
            File report;
            byte[] b ;

            report = new File(getServletConfig().getServletContext().getRealPath("reportes/reporteA.jasper"));

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

    private void verArt(HttpServletRequest request, HttpServletResponse response) {
    }
//This methos helps us to handle weather is an insertion or an update given an existent record from the form
//    This just happens when we are in the form and we must do a POST request
    private void saveArt(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        ArticuloDAO daoA = new ArticuloDAO();
        ArticuloDTO dtoA = new ArticuloDTO();
        CategoriaDTO dtoC = new CategoriaDTO();
        CategoriaDAO daoC = new CategoriaDAO();
        List catList = daoC.setSqlReadAll();
        request.setAttribute("listaCat",catList);
        String id = request.getParameter("idArt");
        if (request.getAttribute("dtoArt")==null && id.equals("")){
            dtoA.getEntidad().setNomArt(request.getParameter("nomA"));
            dtoA.getEntidad().setDescArti(request.getParameter("desA"));
            dtoA.getEntidad().setExistencia(Integer.parseInt(request.getParameter("exsA")));
            dtoA.getEntidad().setPrecio(Float.parseFloat(request.getParameter("price")));
            dtoA.getEntidad().setStockMaximo(Integer.parseInt(request.getParameter("stcMx")));
            dtoA.getEntidad().setStockMinimo(Integer.parseInt(request.getParameter("stcMn")));
            dtoA.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("idCat")));
            System.out.println(request.getParameter("idCat"));
            try{
                daoA.setSqlInsert(dtoA);
                listaArt(request,response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            dtoA.getEntidad().setIdArticulo(Integer.parseInt(id));
            dtoA.getEntidad().setNomArt(request.getParameter("nomA"));
            dtoA.getEntidad().setDescArti(request.getParameter("desA"));
            dtoA.getEntidad().setExistencia(Integer.parseInt(request.getParameter("exsA")));
            dtoA.getEntidad().setPrecio(Float.parseFloat(request.getParameter("price")));
            dtoA.getEntidad().setStockMaximo(Integer.parseInt(request.getParameter("stcMx")));
            dtoA.getEntidad().setStockMinimo(Integer.parseInt(request.getParameter("stcMn")));
            dtoA.getEntidad().setIdCategoria(Integer.parseInt(request.getParameter("idCat")));

            daoA.setSqlUpdate(dtoA);
            listaArt(request,response);
        }
    }

    private void delArt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        ArticuloDAO daoA = new ArticuloDAO();
        ArticuloDTO dtoA = new ArticuloDTO();
        String idA = request.getParameter("id");

        if (idA.equals("")){
                String msgError = "No se pudo eliminar dado que no hubo Id valido";
                request.setAttribute("errorMsg",msgError);
                listaArt(request,response);
        }else{
            dtoA.getEntidad().setIdArticulo(Integer.parseInt(idA));
            try {
                daoA.setSqlDelete(dtoA);
                listaArt(request,response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void actArt(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        ArticuloDAO daoA = new ArticuloDAO();
        ArticuloDTO dtoA = new ArticuloDTO();
        CategoriaDTO dtoC = new CategoriaDTO();
        CategoriaDAO daoC = new CategoriaDAO();
        List catList = daoC.setSqlReadAll();
        request.setAttribute("listaCat",catList);
        dtoA.getEntidad().setIdArticulo(Integer.parseInt(request.getParameter("id")));
        try {
            dtoA = daoA.setSqlRead(dtoA);
            request.setAttribute("dtoA",dtoA);
            RequestDispatcher rD = request.getRequestDispatcher("/articulo/articuloForm.jsp");
            rD.forward(request,response);
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }

//    This methos just redirect us to the Article Form
    private void nueArt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        RequestDispatcher rD = request.getRequestDispatcher("/articulo/articuloForm.jsp");
        CategoriaDTO dtoC = new CategoriaDTO();
        CategoriaDAO daoC = new CategoriaDAO();
        List catList = daoC.setSqlReadAll();
        request.setAttribute("listaCat",catList);
        rD.forward(request,response);
    }

    private void listaArt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        ArticuloDAO daoA = new ArticuloDAO();
        ArticuloDTO dtoA = new ArticuloDTO();
        List list = daoA.setSqlReadAll();
        request.setAttribute("listaArticulo",list);
        RequestDispatcher rD = request.getRequestDispatcher("/articulo/listaArt.jsp");
        rD.forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
        processRequest(request, response);
    } catch (SQLException e) {
        e.printStackTrace();
        }
    }
}
