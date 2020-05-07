package com.daily.english.app.util;

import com.daily.english.app.domain.Video;
import com.daily.english.app.vo.VideoVo;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * 实现 bean copy Mapper
 */
@Component
public class OrikaClassMapper extends ConfigurableMapper {
    @Override
    public void configure(MapperFactory mapperFactory) {

        mapperFactory.classMap(Video.class, VideoVo.class)
                .byDefault()
                .customize(new CustomMapper<Video, VideoVo>() {
                    @Override
                    public void mapAtoB(Video from, VideoVo to, MappingContext mappingContext) {
                        String addDateStr = new DateTime(from.getAddDate()).toString("yyyy-MM-dd HH:mm:ss");
                        to.setAddDate(addDateStr);
                    }
                })
                .register();
    }

}
