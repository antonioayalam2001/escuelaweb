package com.ipn.mx.modelo.dao;



import com.ipn.mx.modelo.DbConnection;
import com.ipn.mx.modelo.dto.CarreraDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Class that contains the main five querys to the database
public class CarreraDAO {
    private static final String SQL_INSERT = "insert into Carrera(nombreCarrera,descripcionCarrera) " +
            " values(?, ?)";
    private static final String SQL_UPDATE = "update  Carrera set nombreCarrera = ?, descripcionCarrera = ? " +
            " where idCarrera=?";
    private static final String SQL_Delete = "Delete from  Carrera " +
            "where idCarrera=?";
    private static final String SQL_SELECT = "Select *from Carrera where idCarrera = ?";
    private static final String SQL_SELECTALL = "Select *from Carrera";
    //    We are use Singleton we cannot create the instance like this
    private DbConnection database = DbConnection.getInstance();
    public Connection conexion;

    //    public DbConnection database = DbConnection.instancia();
    public CarreraDAO() {
    }

    public void setSqlInsert(CarreraDTO dto) throws SQLException {
        conexion = database.getConection();
        //        PreparedStatement allows us to give parameters into each selected query
        PreparedStatement query = null;

        try {
            query = conexion.prepareStatement(SQL_INSERT);
            query.setString(1, dto.getEntidad().getNombreCarrera());
            query.setString(2, dto.getEntidad().getDescripcionCarrera());
            query.executeUpdate();
        } finally {
            if (query != null) query.close();
            conexion.close();
        }
    }

    public void SelectAll(CarreraDTO dto) throws SQLException {
        PreparedStatement query = null;
        ResultSet rs = null;
        try {
            query = conexion.prepareStatement(SQL_SELECTALL);
            rs = query.executeQuery(SQL_SELECTALL);
            while (rs.next()) {
                System.out.println(rs.getString(3));
            }

        } finally {
            if (query != null) query.close();
            conexion.close();
        }
    }

    public void delete(CarreraDTO dto) throws SQLException {
        conexion = database.getConection();
        PreparedStatement query = null;
        try {
            query = conexion.prepareStatement(SQL_Delete);
            query.setInt(1, dto.getEntidad().getIdCarrera());
            query.executeUpdate();
        } finally {
            if (query != null) query.close();
            conexion.close();
        }
    }
    public void update(CarreraDTO dto) throws SQLException {
        conexion = database.getConection();
        PreparedStatement query = null;
        try {
            query = conexion.prepareStatement(SQL_UPDATE);
            query.setString(1,dto.getEntidad().getNombreCarrera());
            query.setString(2,dto.getEntidad().getDescripcionCarrera());
            query.setInt(3, dto.getEntidad().getIdCarrera());
            query.executeUpdate();
        } finally {
            if (query != null) query.close();
            conexion.close();
        }
    }

    public List readAll() throws SQLException {
        conexion = database.getConection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = null;
        try {
            ps = conexion.prepareStatement(SQL_SELECTALL);
            rs = ps.executeQuery();
            lista = obtenerResultados(rs);
            if (lista.size() > 0) {
                return lista;
            } else {
                return null;
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            conexion.close();
        }
    }

    public CarreraDTO read(CarreraDTO dto) throws SQLException {
        conexion = database.getConection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = null;
        try {
            ps = conexion.prepareStatement(SQL_SELECT);
//            ps.setLong(1, dto.getEntidad().getIdCarrera());
            ps.setInt(1, dto.getEntidad().getIdCarrera());
            rs = ps.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()) {
                return (CarreraDTO) lista.get(0);
            } else {
                return null;
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            conexion.close();
        }
    }

    public static void main(String[] args) throws SQLException {
//        CarreraDTO elemento = new CarreraDTO();
//        elemento.getEntidad().setNombreCarrera("Ingenieria en Sistemas");
//        elemento.getEntidad().setDescripcionCarrera("f");
//        CarreraDAO insercion = new CarreraDAO();
//        insercion.Insert(elemento);
////        insercion.SelectAll(elemento);
////        elemento.getEntidad().setIdCarrera(3L);
////        insercion.delete(elemento);
////        insercion.readAll();
//
////        Receiving all element into a list
//        List elementos = insercion.readAll();
////        Printing size of the list
//        System.out.println(elementos.size());
////        Printing the second element from the list
//        System.out.println(elementos.get(1).getClass());

        CarreraDAO dao = new CarreraDAO();
        CarreraDTO dto = new CarreraDTO();
        dto.getEntidad().setIdCarrera(1);
        dto = dao.read(dto);
        System.out.println(dto.getEntidad().getNombreCarrera());




    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
//            Instancia por cada registro
            CarreraDTO dto = new CarreraDTO();
            dto.getEntidad().setIdCarrera(rs.getInt("idCarrera"));
            dto.getEntidad().setNombreCarrera(rs.getString(2));
            dto.getEntidad().setDescripcionCarrera(rs.getString(3));
            resultados.add(dto);
//            This print allows us to know what information we are getting from each object
//            System.out.println(dto);
        }
//Getting just the first element from the List
        //        System.out.println(resultados.get(0));
        return resultados;
    }
}
