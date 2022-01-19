package com.zwedu.rac.infrastructure.repository;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwedu.rac.domain.common.AuthInfoThreadLocal;
import com.zwedu.rac.domain.common.page.Pagination;
import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.domain.entity.BizLineEntity;
import com.zwedu.rac.domain.repository.BizLineRepository;
import com.zwedu.rac.infrastructure.converter.BizLineEntity2PoConverter;
import com.zwedu.rac.infrastructure.converter.BizLinePo2EntityConverter;
import com.zwedu.rac.infrastructure.mapper.BizLineMapper;
import com.zwedu.rac.infrastructure.po.BizLinePo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务线存储类
 *
 * @author qingchuan
 * @date 2020/12/10
 */
@Repository
public class BizLineMybatisRepository implements BizLineRepository {
    @Resource
    private BizLineMapper bizLineMapper;

    @Override
    public Pagination<BizLineEntity> listPage(Integer pageNo, Integer pageSize, String searchVal) {
        try (Page page = PageHelper.startPage(pageNo, pageSize)) {
            AuthInfo authInfo = AuthInfoThreadLocal.AUTH_INFO.get();
            List<BizLinePo> poList = bizLineMapper.listPage(searchVal,
                    authInfo);
            List<BizLineEntity> entityList = BizLinePo2EntityConverter.INSTANCE.toEntityList(poList);
            Pagination<BizLineEntity> pagination =
                    new Pagination(pageNo, pageSize, page.getTotal());
            pagination.setDataList(entityList);
            return pagination;
        }
    }

    @Override
    public List<BizLineEntity> listAuthBizLine() {
        AuthInfo authInfo = AuthInfoThreadLocal.AUTH_INFO.get();
        return BizLinePo2EntityConverter.INSTANCE.toEntityList(bizLineMapper.listAuthBizLine(authInfo));
    }

    @Override
    public BizLineEntity queryById(Long id) {
        BizLinePo record = bizLineMapper.selectByPrimaryKey(id);
        return BizLinePo2EntityConverter.INSTANCE.toEntity(record);
    }

    @Override
    public Boolean hasSameEnName(String enName, Long id) {
        return bizLineMapper.queryByEnName(enName, id) != null;
    }

    @Override
    public Boolean hasSameCnName(String cnName, Long id) {
        return bizLineMapper.queryByCnName(cnName, id) != null;
    }

    @Override
    public void insert(BizLineEntity record) {
        bizLineMapper.insertSelective(BizLineEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public void edit(BizLineEntity record) {
        bizLineMapper.updateByPrimaryKeySelective(BizLineEntity2PoConverter.INSTANCE.toPo(record));
    }


    @Override
    public void delete(BizLineEntity record) {
        bizLineMapper.updateByPrimaryKeySelective(BizLineEntity2PoConverter.INSTANCE.toPo(record));
    }

    @Override
    public BizLineEntity queryByEnName(String enName) {
        BizLinePo bizLinePo = bizLineMapper.queryByEnName(enName, null);
        return BizLinePo2EntityConverter.INSTANCE.toEntity(bizLinePo);
    }
}
