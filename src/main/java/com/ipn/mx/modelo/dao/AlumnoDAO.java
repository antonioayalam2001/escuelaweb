package com.ipn.mx.modelo.dao;



import com.ipn.mx.modelo.DbConnection;
import com.ipn.mx.modelo.dto.AlumnoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {
    private static final String SQL_INSERT = "insert into Alumno(nombreAlumno,paternoAlumno,maternoAlumno,emailAlumno,idCarrera) " +
            " values(?, ?,?,?,?)";
    private static final String SQL_UPDATE = "update  Alumno set nombreAlumno = ?, maternoAlumno = ? ,paternoAlumno = ? , emailAlumno =? ,idCarrera=?" +
            "  where idAlumno=?";
    private static final String SQL_Delete = "Delete from  Alumno " +
            "  where idAlumno=?";
    private static final String SQL_SELECT = "Select *from Alumno where idAlumno = ?";
    private static final String SQL_SELECTALL = "Select *from Alumno";
    //    Since we are using the Singleton mode we cannot do this anymore
//    private DbConnection conexion = DbConnection.instancia();
//    With singleton we have to declare like this:
    private DbConnection database = DbConnection.getInstance();
    Connection conexion = database.getConection();

    public AlumnoDAO() {
    }

    public void setSqlInsert (AlumnoDTO alu) throws SQLException {

        PreparedStatement st = null;
        try{
            st = database.getConection().prepareStatement(SQL_INSERT);
            st.setString(1,alu.getEntidad().getNombreAlumno());
            st.setString(2,alu.getEntidad().getPaternoAlumno());
            st.setString(3,alu.getEntidad().getMaternoAlumno());
            st.setString(4,alu.getEntidad().getEmailAlumno());
            st.setInt(5,alu.getEntidad().getIdCarrera());
            st.executeUpdate();
        } finally {
            if (st != null) st.close();
            conexion.close();
        }
    }
    //    Second parameter is the ID we want to modify
    public void setSqlUpdate (AlumnoDTO alu, int idAlumno) throws SQLException{
        PreparedStatement st = null;
        try{
            st = database.getConection().prepareStatement(SQL_UPDATE);
            st.setString(1,alu.getEntidad().getNombreAlumno());
            st.setString(2,alu.getEntidad().getPaternoAlumno());
            st.setString(3,alu.getEntidad().getMaternoAlumno());
            st.setString(4,alu.getEntidad().getEmailAlumno());
            st.setInt(5,alu.getEntidad().getIdCarrera());
            st.setInt(6,idAlumno);
            st.executeUpdate();
        } finally {
            if (st != null) st.close();
            conexion.close();
        }
    }

    public void setDelete (AlumnoDTO alu) throws SQLException{
        PreparedStatement st = null;
        try{
            st= conexion.prepareStatement(SQL_Delete);
            st.setInt(1,alu.getEntidad().getIdAlumno());
            st.executeUpdate();
        }finally{
            if (st!=null)st.close();
            conexion.close();
        }
    }

    public AlumnoDTO read (AlumnoDTO dto) throws SQLException{
        conexion = database.getConection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List lista = null;
        try{
            st = conexion.prepareStatement(SQL_SELECT);
            st.setInt(1,dto.getEntidad().getIdAlumno());
            rs = st.executeQuery();
            lista = obtenerResultados(rs);
            if (!lista.isEmpty()) {
                return (AlumnoDTO) lista.get(0);
            }else{
                return null;
            }
        }finally {
            if (st == null) {
                st.close();
            }
            if (conexion == null) {
                conexion.close();
            }
        }
    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList<>();
        while (rs.next()){
            AlumnoDTO alumno = new AlumnoDTO();
            alumno.getEntidad().setIdAlumno(rs.getInt(1));
            alumno.getEntidad().setNombreAlumno(rs.getString(2));
            alumno.getEntidad().setPaternoAlumno(rs.getString(3));
            alumno.getEntidad().setMaternoAlumno(rs.getString(4));
            alumno.getEntidad().setEmailAlumno(rs.getString(5));
            alumno.getEntidad().setIdCarrera(rs.getInt(6));
            resultados.add(alumno);
        }
        return resultados;
    }

    public List readAll() throws SQLException{
//        conexion = database.getConection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List lista = null;
        try{
            ps = conexion.prepareStatement(SQL_SELECTALL);
            rs = ps.executeQuery();
            lista = obtenerResultados(rs);
            if (lista.size()>0) {
                return  lista;
            }
        }finally {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
            conexion.close();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
//        this entitity gives us access to our object in Java so we can set and get all the necessary data
        AlumnoDTO newAlumno = new AlumnoDTO();
//        This object allows us to send and retrive the information from the application to MySql
//        Parameter is the previous object.
        AlumnoDAO alumnoAcceso = new AlumnoDAO();
//        alumnoAcceso.setSqlInsert(newAlumno);
//        newAlumno.getEntidad().setNombreAlumno("Ramon");
//        newAlumno.getEntidad().setIdCarrera(1);
//        alumnoAcceso.setSqlUpdate(newAlumno,1);

        List alumnos =alumnoAcceso.readAll();
//        System.out.println(alumnos.size());
        AlumnoDTO alumno0 = new AlumnoDTO();
        alumno0 = (AlumnoDTO) alumnos.get(0);
        System.out.println(alumno0.getEntidad().getEmailAlumno());

        alumno0.getEntidad().setNombreAlumno("Juan");
        alumnoAcceso.setSqlUpdate(alumno0,2);

    }
}
