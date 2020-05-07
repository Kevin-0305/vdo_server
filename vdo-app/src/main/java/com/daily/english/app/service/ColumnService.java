package com.daily.english.app.service;

import com.daily.english.app.domain.Column;
import com.daily.english.app.mapper.ColumnMapper;
import com.daily.english.app.vo.MapVo;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ColumnService {

    @Autowired
    private ColumnMapper columnMapper;

    @Autowired
    private MapperFacade mapper;

    public void saveColumn(Column column) {
        columnMapper.insert(column);
    }

    public List<Column> getColumnList() {
        List<Column> columnList = columnMapper.findColumnList();
        return columnList;
    }

}
