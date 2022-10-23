package cn.edu.whut.springbear.course.service.vod.service;

import cn.edu.whut.springbear.course.model.pojo.vod.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @datetime 2022-10-21 16:18
 */
public interface SubjectService extends IService<Subject> {
    /**
     * 获取课程目录下的子课程
     *
     * @param parentId 课程父 ID
     * @return 子课程列表
     */
    List<Subject> listSubCourses(long parentId);

    /**
     * 课程分类 excel 文件导出
     *
     * @param response response header object
     */
    void exportCourseData(HttpServletResponse response);

    /**
     * 课程分类数据导入数据库
     *
     * @param file     课程分类 Excel 文件
     * @param realPath 工程磁盘真实路径
     * @return true：导入成功
     */
    boolean importCourseData(MultipartFile file, String realPath);
}
