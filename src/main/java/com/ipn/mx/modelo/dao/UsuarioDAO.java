package com.ipn.mx.modelo.dao;



import com.ipn.mx.modelo.DbConnection;
import com.ipn.mx.modelo.dto.UsuarioDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static final String SQL_INSERT = "insert into Usuario(nombre,apellidoP,apellidoM,usuario,clave,fechaCreacion) " +
            " values(?,?,?,?,?,CURDATE())";
    private static final String SQL_UPDATE = "update  Usuario set nombre = ?, apellidoP = ? , apellidoM=?,usuario=?,clave=? " +
            " where idUsuario=?";
    private static final String SQL_Delete = "Delete from  Usuario " +
            "where idUsuario=?";
    private static final String SQL_SELECT = "Select *from Usuario where idUsuario = ?";
    private static final String SQL_SELECTALL = "Select *from Usuario";
    private static final String SQL_MATCH = "Select *from usuario where usuario= ? and clave = ?";
    //    We are use Singleton we cannot create the instance like this
    private DbConnection database = DbConnection.getInstance();
    public Connection conexion;

    //    public DbConnection database = DbConnection.instancia();
    public UsuarioDAO() {
    }

    public UsuarioDTO setSqlMatch(UsuarioDTO dto) throws SQLException{
        conexion = database.getConection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = null;
        try {
            ps = conexion.prepareStatement(SQL_MATCH,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
//            ps.setLong(1, dto.getEntidad().getIdUsuario());
            ps.setString(1, dto.getEntidad().getUsuario());
            ps.setString(2, dto.getEntidad().getClave());
            rs = ps.executeQuery();
            lista = obtenerResultados(rs);
            if (rs.first()) {
                return (UsuarioDTO) lista.get(0);
            } else {
                System.out.println("NO se encontraron resgistros con esos datos");
                return null;
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            conexion.close();
        }
    }

    public void setSqlInsert(UsuarioDTO dto) throws SQLException {
        conexion = database.getConection();
        //        PreparedStatement allows us to give parameters into each selected query
        PreparedStatement query = null;
        try {
            query = conexion.prepareStatement(SQL_INSERT);
            query.setString(1, dto.getEntidad().getNomU());
            query.setString(2, dto.getEntidad().getApeP());
            query.setString(3, dto.getEntidad().getApeM());
            query.setString(4, dto.getEntidad().getUsuario());
            query.setString(5, dto.getEntidad().getClave());
            query.executeUpdate();
        } finally {
            if (query != null) query.close();
            conexion.close();
        }
    }

    public void SelectAll(UsuarioDTO dto) throws SQLException {
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

    public void delete(UsuarioDTO dto) throws SQLException {
        conexion = database.getConection();
        PreparedStatement query = null;
        try {
            query = conexion.prepareStatement(SQL_Delete);
            query.setInt(1, dto.getEntidad().getId());
            query.executeUpdate();
        } finally {
            if (query != null) query.close();
            conexion.close();
        }
    }
    public void update(UsuarioDTO dto) throws SQLException {
        conexion = database.getConection();
        PreparedStatement query = null;
        try {
            query = conexion.prepareStatement(SQL_UPDATE);
            query.setString(1, dto.getEntidad().getNomU());
            query.setString(2, dto.getEntidad().getApeP());
            query.setString(3, dto.getEntidad().getApeM());
            query.setString(4, dto.getEntidad().getUsuario());
            query.setString(5, dto.getEntidad().getClave());
            query.setInt(6,dto.getEntidad().getId());
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

    public UsuarioDTO read(UsuarioDTO dto) throws SQLException {
        conexion = database.getConection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = null;
        try {
            ps = conexion.prepareStatement(SQL_SELECT);
//            ps.setLong(1, dto.getEntidad().getIdUsuario());
            ps.setInt(1, dto.getEntidad().getId());
            rs = ps.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()) {
                return (UsuarioDTO) lista.get(0);
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

        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
//        dto.getEntidad().setUsuario("TonyAyala");
//        dto.getEntidad().setClave("200120");

//        dto.getEntidad().setId(1);
        dto.getEntidad().setUsuario("Antonio");
        dto.getEntidad().setClave("kjkjk");
        dto = dao.setSqlMatch(dto);

        System.out.println(dto.getEntidad().getApeM());

    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
//            Instancia por cada registro
            UsuarioDTO dto = new UsuarioDTO();
            dto.getEntidad().setId(rs.getInt(1));
            dto.getEntidad().setNomU(rs.getString(2));
            dto.getEntidad().setApeP(rs.getString(3));
            dto.getEntidad().setApeM(rs.getString(4));
            dto.getEntidad().setUsuario(rs.getString(5));
            dto.getEntidad().setClave(rs.getString(6));
            dto.getEntidad().setFechaC(rs.getString(7));
            resultados.add(dto);
//            This print allows us to know what information we are getting from each object
//            System.out.println(dto);
        }
//Getting just the first element from the List
        //        System.out.println(resultados.get(0));
        return resultados;
    }
}
