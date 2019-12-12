package com.xblog.controller.admin;

import com.xblog.common.PageResult;
import com.xblog.commons.response.RespEntity;
import com.xblog.entity.sys.Tag;
import com.xblog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/tag")
@Slf4j
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 增加标签
     * @return RespEntity
     */
    @RequestMapping(value = "/addTag", method = RequestMethod.POST)
    public RespEntity add(@RequestBody Tag tag){
        log.info("step into TagController addTag(), name: {}", tag);
        if (StringUtils.isBlank(tag.getName())){
            return RespEntity.error("请输入标签名称");
        }
        tag = tagService.add(tag);
        if (tag != null){
            return RespEntity.success(tag, "添加成功");
        }else {
            return RespEntity.error("添加标签失败");
        }
    }

    /**
     * 修改标签
     * @return RespEntity
     */
    @RequestMapping(value = "/updateTag", method = RequestMethod.PUT)
    public RespEntity updateTag(String name, String id){
        log.info("step into TagController updateTag(), name: {}, id: {}", name, id);
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
    public RespEntity findById(String id){
        log.info("step into TagController findById(), id: {}", id);
        Tag tag = tagService.findById(id);
        return RespEntity.success(tag);
    }

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findAllTag", method = RequestMethod.GET)
    public RespEntity findAllTag(){
        log.info("step into TagController findAllTag()");
        PageResult<Tag> pageResult = tagService.findAll(null, 1, 200);
        return RespEntity.success(pageResult.getRows(), pageResult.getCount());
    }

    /**
     * 查询所有标签
     * @return RespEntity
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public RespEntity findAll(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "limit", required = false, defaultValue = "20") int limit
    ){
        log.info("step into TagController findAll(), pageNum: {}, pageSize: {}, key: {}", page, limit, key);
        PageResult<Tag> pageResult = tagService.findAll(key, page, limit);
        return RespEntity.success(pageResult.getRows(), pageResult.getCount());
    }

    /**
     * 删除
     * @return RespEntity
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public RespEntity delete(String id){
        log.info("step into TagController delete(), id: {}", id);
        tagService.deleteById(id);
        return RespEntity.success("删除成功");
    }

    /**
     * 删除
     * @return RespEntity
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    public RespEntity delete(@RequestParam(value = "ids[]") String[] ids){
        log.info("step into TagController delete(), ids: %s", ids);
        if (ids != null && ids.length > 0){
            for (String id: ids) {
                tagService.deleteById(id);
            }
        }
        return RespEntity.success("删除成功");
    }
}
