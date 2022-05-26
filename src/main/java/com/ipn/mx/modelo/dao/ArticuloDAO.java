package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.DbConnection;
import com.ipn.mx.modelo.dto.ArticuloDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO {
//    This one just need to implement the part of getting the categoryId
//
    //    To use it with Postgre just remove brackets


    //    needs name and description
    private static final String SQL_INSERT = "insert into \"Articulo\" (\"nomArti\" ,\"descArti\",\"existencia\",\"stockMinimo\",\"stockMaximo\",precio,\"categoriaId\") values (?,?,?,?,?,?,?);";
    //    receive id to delete
    private static final String SQL_DELETE = "call del_art(?)";
    //    receive name, description and an Id
    private static final String SQL_UPDATE = "call upd_art(?,?,?,?,?,?,?,?)";

    private static final String SQL_READ = "select *from \"Articulo\" where \"idArticulo\" = ?";
    //    no parameters needed
    private static final String SQL_READALL = "select *from \"Articulo\"";
    //Connection to the dataBase
    DbConnection db = DbConnection.getInstance();
    Connection connection;

    //    Insert into method
    public void setSqlInsert (ArticuloDTO dto) throws SQLException {
        connection = db.getConection();
        CallableStatement cs = null;
        try{
            cs = connection.prepareCall(SQL_INSERT);
            cs.setString(1,dto.getEntidad().getNomArt());
            cs.setString(2,dto.getEntidad().getDescArti());
            cs.setInt(3,dto.getEntidad().getExistencia());
            cs.setInt(4,dto.getEntidad().getStockMinimo());
            cs.setInt(5,dto.getEntidad().getStockMaximo());
            cs.setFloat(6,dto.getEntidad().getPrecio());
            cs.setInt(7,dto.getEntidad().getIdCategoria());
            cs.executeUpdate();
        }finally {
            if (cs!=null)cs.close();
            if (connection!=null)connection.close();
        }
    }

    //    Insert delete method
    public void setSqlDelete (ArticuloDTO dto) throws SQLException {
        connection = db.getConection();
        CallableStatement cs = null;
        try{
            cs = connection.prepareCall(SQL_DELETE);
            cs.setInt(1,dto.getEntidad().getIdArticulo());
            cs.executeUpdate();
        }finally {
            if (cs!=null)cs.close();
            if (connection!=null)connection.close();
        }
    }

    //    Insert delete method
    public void setSqlUpdate (ArticuloDTO dto) throws SQLException {
        connection = db.getConection();
        CallableStatement cs = null;
        try{
            cs = connection.prepareCall(SQL_UPDATE);
            cs.setString(1,dto.getEntidad().getNomArt());
            cs.setString(2,dto.getEntidad().getDescArti());
            cs.setInt(3,dto.getEntidad().getExistencia());
            cs.setInt(4,dto.getEntidad().getStockMinimo());
            cs.setInt(5,dto.getEntidad().getStockMinimo());
            cs.setFloat(6,dto.getEntidad().getPrecio());
            cs.setInt(7,dto.getEntidad().getIdCategoria());
            cs.setInt(8,dto.getEntidad().getIdArticulo());
            cs.executeUpdate();
        }finally {
            if (cs!=null)cs.close();
            if (connection!=null)connection.close();
        }
    }


    public List setSqlReadAll() throws SQLException{
        connection = db.getConection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List lista = null;
        try{
            cs = connection.prepareCall(SQL_READALL);
            rs = cs.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()){
                return lista;
            }else {
                System.out.println("Lista vacía");
                return null;
            }
        }finally {
            if (cs!=null)cs.close();
            if (connection!=null)connection.close();
        }
    }
    public ArticuloDTO setSqlRead(ArticuloDTO dto) throws SQLException{
        connection = db.getConection();
        CallableStatement cs = null;
        ResultSet rs = null;
        List lista = null;
        try{
            cs = connection.prepareCall(SQL_READ);
            cs.setInt(1,dto.getEntidad().getIdArticulo());
            rs = cs.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()){
                return (ArticuloDTO) lista.get(0);
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
            ArticuloDTO dto = new ArticuloDTO();
            dto.getEntidad().setIdArticulo(rs.getInt(1));
            dto.getEntidad().setNomArt(rs.getString(2));
            dto.getEntidad().setDescArti(rs.getString(3));
            dto.getEntidad().setExistencia(rs.getInt(4));
            dto.getEntidad().setStockMinimo(rs.getInt(5));
            dto.getEntidad().setStockMaximo(rs.getInt(6));
            dto.getEntidad().setPrecio(rs.getFloat(7));
            dto.getEntidad().setIdCategoria(rs.getInt(8));
            resultados.add(dto);
        }
        return resultados;
    }


    public static void main(String[] args) throws SQLException {
        ArticuloDTO dto = new ArticuloDTO();
        ArticuloDAO dao = new ArticuloDAO();

//        dto.getEntidad().setIdArticulo(7);
//        dto = dao.setSqlRead(dto);
//        System.out.println(dto.getEntidad().getStockMinimo());


//        dto.getEntidad().setIdArticulo(5);
//        dao.setSqlDelete(dto);
////
////        dto.getEntidad().setIdArticulo(1);
////        dto.getEntidad().setNomArt("Gelatina");
////        dto.getEntidad().setDescArti("Baja en calorias");
////        dto.getEntidad().setExistencia(12);
////        dto.getEntidad().setStockMinimo(10);
////        dto.getEntidad().setStockMaximo(100);
////        dto.getEntidad().setPrecio(12.23);
////
////        dao.setSqlInsert(dto);
////        dto.getEntidad().setIdCategoria(45);
////        dao.setSqlDelete(dto);
//        List list = dao.setSqlReadAll();
//        for (Object dtoE : list){
//            ArticuloDTO elemento = (ArticuloDTO)dtoE;
//            System.out.println(elemento.getEntidad().getStockMinimo());
//        }
//        for (Object dtoE: list
//             ) {
//             ArticuloDTO elemento = (ArticuloDTO) dtoE;
//            System.out.println(elemento.entidad.getIdCategoria());
//
//
//        }


//        System.out.println(list);

    }



}
