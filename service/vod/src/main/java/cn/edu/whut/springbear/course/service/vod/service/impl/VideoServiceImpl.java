package cn.edu.whut.springbear.course.service.vod.service.impl;

import cn.edu.whut.springbear.course.model.pojo.vod.Video;
import cn.edu.whut.springbear.course.service.vod.mapper.VideoMapper;
import cn.edu.whut.springbear.course.service.vod.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Spring-_-Bear
 * @since 2022-10-22
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Override
    public void deleteVideosOfCourse(Long courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }
}
