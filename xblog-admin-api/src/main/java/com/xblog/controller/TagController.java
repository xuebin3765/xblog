package com.xblog.controller;

import com.xblog.commons.response.RespEntity;
import com.xblog.entity.sys.Tag;
import com.xblog.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/tag")
public class TagController {

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    @Resource
    private TagService tagService;

    /**
     * 增加标签
     * @return RespEntity
     */
    @RequestMapping(value = "/addTag", method = RequestMethod.POST)
    public RespEntity add(@RequestParam("name") String name){
        logger.debug("step into TagController add(), name: {}", name);
        if (StringUtils.isBlank(name)){
            return RespEntity.error("请输入标签名称");
        }
        Tag tag = new Tag(name);
        tag = tagService.add(tag);
        if (tag != null){
            return RespEntity.success(null);
        }else {
            return RespEntity.error("添加标签失败");
        }
    }

    /**
     * 修改标签
     * @return RespEntity
     */
    @RequestMapping(value = "/updateTag", method = RequestMethod.PUT)
    public RespEntity updateTag(String name, int id){
        logger.debug("step into TagController updateTag(), name: {}, id: {}", name, id);
        Tag tag = tagService.findById(id);
        if (tag == null){
            return RespEntity.error("标签不存在");
        }
        Tag tagName = tagService.findByName(name);
        if (tagName != null){
            return RespEntity.error("标签名已存在");
        }
        tag.setName(name);
        tag = tagService.add(tag);
        if (tag == null){
            return RespEntity.error("修改标签失败");
        }
        return RespEntity.success(tag);
    }

    /**
     * 查询单个标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    public RespEntity findById(int id){
        logger.debug("step into TagController findById(), id: {}", id);
        Tag tag = tagService.findById(id);
        return RespEntity.success(tag);
    }

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public RespEntity findAll(
            @RequestParam("pageNum") int pageNum,
            @RequestParam("pageSize") int pageSize
    ){
        logger.debug("step into TagController findAll(), pageNum: {}, pageSize: {}", pageNum, pageSize);
        List<Tag> tagList = tagService.findAll(pageNum, pageSize);
        return RespEntity.success(tagList);
    }

    /**
     * 删除
     * @return RespEntity
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.DELETE)
    public RespEntity delete(int id){
        logger.debug("step into TagController delete(), id: {}", id);
        tagService.deleteById(id);
        return RespEntity.success("删除成功");
    }
}
