package repository;

import db.DBConnect;
import dto.TelDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelBookRepository {
    private final Connection conn;
    // 1. DB 연결
    public TelBookRepository(Connection conn) {
        this.conn = conn;
    }

    public int insertData(TelDto dto) {

        PreparedStatement psmt = null;
        // 2. 쿼리 생성
        // 실행 결과를 담을 변수
        int result = 0;
        try {
            String sql = "INSERT INTO telbook (name, age, address, phone) VALUES (?,?,?,?)";
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, dto.getName());
            psmt.setInt(2, dto.getAge());
            psmt.setString(3, dto.getAddress());
            psmt.setString(4, dto.getTelNum());
            //insert, update, delete는 executeUpdate()로 결과를 받는다.
            //Select만 execute()로 결과 받는다.
            result = psmt.executeUpdate();
            psmt.close();
        } catch (Exception e) {
            System.out.println("INSERT 오류 : " + e.getMessage());
        }
        return result;
        // 3. 쿼리 실행
        // 4. 정리
    }

    public List<TelDto> findAll() {
        List<TelDto> list = new ArrayList<>();
        //쿼리를 실행할 도구
        PreparedStatement psmt = null;
        //검색 결과 레코드 셋을 담을 통
        ResultSet rs = null;
        try {
            //쿼리 작성
            String sql = "SELECT * FROM telbook ORDER BY name";
            psmt = conn.prepareStatement(sql);
            //실행 -> 결과는 rs이 받는다
            rs=psmt.executeQuery();
            //받은 결과를 DTO List에 차곡차곡 담는다.
            //rs.next() : 다음 레코드가 있니?
            while (rs.next()) {
                // 읽어온 레코드를 담을 빈 DTO를 생성
                TelDto dto = new TelDto();
                dto.setId(rs.getLong("id"));
                dto.setName(rs.getString("name"));
                dto.setAge(rs.getInt("age"));
                dto.setAddress(rs.getString("address"));
                dto.setTelNum(rs.getString("phone"));
                //System.out.println(dto);
                //만들어진 dto를 list에 담는다
                list.add(dto);
            }
            psmt.close();
            rs.close();
        } catch (Exception e){
            System.out.println("Find All Error : " + e.getMessage());
        }

        return list;
    }

    public List<TelDto> findById(int id) {
        List<TelDto> list = new ArrayList<>();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM telbook WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, id);
            rs=psmt.executeQuery();
            while (rs.next()) {
                TelDto dto = new TelDto();
                dto.setId(rs.getLong("id"));
                dto.setName(rs.getString("name"));
                dto.setAge(rs.getInt("age"));
                dto.setAddress(rs.getString("address"));
                dto.setTelNum(rs.getString("phone"));
//                System.out.println(dto);
                list.add(dto);
            }
            psmt.close();
            rs.close();
        } catch ( Exception e){
            System.out.println("Find All Error : " + e.getMessage());
        }
        return list;
    }

    public int deleteById(int id) {
        PreparedStatement psmt = null;
        int result = 0;
        try {
            String sql = "DELETE FROM telbook WHERE id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, id);
            result = psmt.executeUpdate();
            psmt.close();
        } catch (Exception e) {
            System.out.println("Delete All Error : " + e.getMessage());
        }
        return result;
    }

    public void update(TelDto updateData) {
        PreparedStatement psmt = null;
        // 2. 쿼리 생성
        // 실행 결과를 담을 변수
        int result = 0;
        try {
            String sql = "UPDATE telBook ";
            sql = sql + " SET name=?, ";
            sql = sql + " age=?, ";
            sql = sql + " address=?, ";
            sql = sql + " phone=? " ;
            sql = sql + " WHERE id=?";

            psmt = conn.prepareStatement(sql);
            psmt.setString(1, updateData.getName());
            psmt.setInt(2, updateData.getAge());
            psmt.setString(3, updateData.getAddress());
            psmt.setString(4, updateData.getTelNum());
            psmt.setLong(5, updateData.getId());
            //insert, update, delete는 executeUpdate()로 결과를 받는다.
            //Select만 execute()로 결과 받는다.
            psmt.executeUpdate();
            psmt.close();
        } catch (Exception e) {
            System.out.println("INSERT 오류 : " + e.getMessage());
        }
        // 3. 쿼리 실행
        // 4. 정리
    }
}
