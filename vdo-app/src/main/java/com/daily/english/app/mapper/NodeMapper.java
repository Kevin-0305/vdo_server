package com.daily.english.app.mapper;

import com.daily.english.app.domain.Node;
import com.daily.english.app.domain.Part;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NodeMapper {

    @Insert("insert into ed_node(part_id, question_id, text, type, choose_type, title_text, title_img, " +
            "option_list_json, img_fill_content_list_json, number, left_json, right_json, " +
            "answer, orders, status, create_time, update_time, create_account, " +
            "update_account) " +
            "values(#{partId}, #{questionId}, #{text}, #{type}, #{chooseType}, #{titleText}, #{titleImg}, " +
            "#{optionListJson}, #{imgFillContentListJson}, #{number}, #{leftJson}, #{rightJson}, " +
            "#{answer}, #{orders}, #{status}, #{createTime}, #{updateTime}, #{createAccount}, #{updateAccount})")
    @Options(useGeneratedKeys = true, keyProperty="nodeId", keyColumn = "node_id")
    void insertNode(Node node);

    @Update("update ed_node set question_id=#{questionId}, text=#{text}, type=#{type}, choose_type=#{chooseType}, " +
            "title_text=#{titleText}, title_img=#{titleImg}, option_list_json=#{optionListJson}, " +
            "img_fill_content_list_json=#{imgFillContentListJson}, number=#{number}, left_json=#{leftJson}, " +
            "right_json=#{rightJson}, answer=#{answer}, orders=#{orders}, status=#{status}, " +
            "update_time=#{updateTime}, update_account=#{updateAccount} " +
            "where node_id=#{nodeId}")
    int updateNode(Node node);

    @Delete("delete from ed_node where part_id = #{id}")
    void deleteNodeByPartId(@Param("id") Long id);

    @Select("select node_id, part_id, question_id, text, type, choose_type, title_text, title_img, " +
            "option_list_json, img_fill_content_list_json, number, left_json, right_json, " +
            "answer, orders, status, create_time, update_time, create_account, " +
            "update_account " +
            "from ed_node where part_id = #{id}")
    Node findNodeByPartId(@Param("id") Long id);

//    @Select("select node_id, part_id, text, orders, status, create_time, update_time, create_account, " +
//            "update_account " +
//            "from ed_node")
//    List<Node> findNodeList();

    @Select({"<script>" +
            "select node_id, part_id, question_id, text, type, choose_type, title_text, title_img, " +
            "option_list_json, img_fill_content_list_json, number, left_json, right_json, " +
            "answer, orders, status, create_time, update_time, create_account, " +
            "update_account " +
            "from ed_node where part_id in " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>#{id}</foreach> " +
            "order by orders " +
            "</script>"})
    List<Node> findNodeListByPartIds(@Param("ids") List<Long> ids);

}
