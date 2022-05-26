package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.DbConnection;
import com.ipn.mx.modelo.dto.CarreraDTO;
import com.ipn.mx.modelo.dto.CategoriaDTO;
import com.ipn.mx.modelo.entidades.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
//    To use it with Postgre just remove brackets


//    needs name and description
    private static final String SQL_INSERT = "call spInsertar(?,?)";
//    receive id to delete
    private static final String SQL_DELETE = "call spEliminar(?)";
//    receive name, description and an Id
    private static final String SQL_UPDATE = "call spActualizar (?,?,?)";
//    needs an id
    private static final String SQL_READ = "select *from \"Categoria\" where \"idCategoria\" = ?";
//    no parameters needed
    private static final String SQL_READALL = "select *from \"Categoria\"";
//Connection to the dataBase
    DbConnection db = DbConnection.getInstance();
    Connection connection;

//    Insert into method
    public void setSqlInsert (CategoriaDTO dto) throws SQLException {
        connection = db.getConection();
        CallableStatement cs = null;
        try{
            cs = connection.prepareCall(SQL_INSERT);
            cs.setString(1,dto.getEntidad().getNombreCategoria());
            cs.setString(2,dto.getEntidad().getDescripcionCategoria());
            cs.executeUpdate();
        }finally {
            if (cs!=null)cs.close();
            if (connection!=null)connection.close();
        }
    }

    //    Insert delete method
    public void setSqlDelete (CategoriaDTO dto) throws SQLException {
        connection = db.getConection();
        CallableStatement cs = null;
        try{
            cs = connection.prepareCall(SQL_DELETE);
            cs.setInt(1,dto.getEntidad().getIdCategoria());
            cs.executeUpdate();
        }finally {
            if (cs!=null)cs.close();
            if (connection!=null)connection.close();
        }
    }

    //    Insert delete method
    public void setSqlUpdate (CategoriaDTO dto) throws SQLException {
        connection = db.getConection();
        CallableStatement cs = null;
        try{
            cs = connection.prepareCall(SQL_UPDATE);
            cs.setString(1,dto.getEntidad().getNombreCategoria());
            cs.setString(2,dto.getEntidad().getDescripcionCategoria());
            cs.setInt(3,dto.getEntidad().getIdCategoria());
            cs.executeUpdate();
        }finally {
            if (cs!=null)cs.close();
            if (connection!=null)connection.close();
        }
    }


    public List setSqlReadAll() throws SQLException{
        connection = db.getConection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = null;
        try{
            ps = connection.prepareStatement(SQL_READALL);
            rs = ps.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()){
                return lista;
            }else {
                System.out.println("Lista vacía");
                return null;
            }
        }finally {
            if (ps!=null)ps.close();
            if (connection!=null)connection.close();
        }
    }
    public CategoriaDTO setSqlRead(CategoriaDTO dto) throws SQLException{
        connection = db.getConection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List lista = null;
        try{
            cs = connection.prepareCall(SQL_READ);
            cs.setInt(1,dto.getEntidad().getIdCategoria());
            rs = cs.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()){
                return (CategoriaDTO) lista.get(0);
            }else {
                System.out.println("No hay coincidencia con lo que se desea hacer");
                return null;
            }
        }finally {
            if (cs!=null)cs.close();
            if (connection!=null)connection.close();
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList<>();
        while (rs.next()){
            CategoriaDTO dto = new CategoriaDTO();
            dto.getEntidad().setNombreCategoria(rs.getString(2));
            dto.getEntidad().setDescripcionCategoria(rs.getString(3));
            dto.getEntidad().setIdCategoria(rs.getInt(1));
            resultados.add(dto);
        }
        return resultados;
    }


    public static void main(String[] args) throws SQLException {
        CategoriaDTO dto = new CategoriaDTO();
        CategoriaDAO dao = new CategoriaDAO();


//        dto.getEntidad().setNombreCategoria("Nueva");
//        dto.getEntidad().setDescripcionCategoria("Insercion");
//        dao.setSqlInsert(dto);
//        dto.getEntidad().setIdCategoria(45);
//        dao.setSqlDelete(dto);
        List list = dao.setSqlReadAll();
        for (Object dtoE : list){
            CategoriaDTO elemento = (CategoriaDTO)dtoE;
            System.out.println(elemento.getEntidad().getNombreCategoria());
        }
//        dto.getEntidad().setIdCategoria(5);
//        dto = dao.setSqlRead(dto);
//        System.out.println(dto.getEntidad().getNombreCategoria());

//        for (Object dtoE: list
//             ) {
//             CategoriaDTO elemento = (CategoriaDTO) dtoE;
//            System.out.println(elemento.entidad.getIdCategoria());
//
//
//        }


//        System.out.println(list);

    }


}


