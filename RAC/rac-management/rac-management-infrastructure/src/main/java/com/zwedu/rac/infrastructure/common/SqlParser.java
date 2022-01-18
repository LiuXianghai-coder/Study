package com.zwedu.rac.infrastructure.common;

import com.google.common.collect.ImmutableList;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;

/**
 * sql解析器
 *
 * @author qingchuan
 * @date 2020/12/26
 */
public class SqlParser {

    public static void main(String[] args) throws JSQLParserException {
        String sql = "select * from tb_biz_entity where biz_line_id = 1";
        Statement statement = null;
        statement = CCJSqlParserUtil.parse(sql);
        // 非查询方法，直接报错
        Select select = (Select) statement;
        // 获取对应的selectBody
        SelectBody selectBody = select.getSelectBody();
        // 判断是否是join查询
        // 需要区分是否需要添加扩展表 ，判断需求，查询字段，where字段，order by 字段 group by 字段
        PlainSelect plainSelect = (PlainSelect) selectBody;
        Expression expression = plainSelect.getWhere();
        Parenthesis parenthesis = new Parenthesis(expression);
        ExpressionList itemsList = new ExpressionList();
        itemsList.setExpressions(ImmutableList.of(new LongValue(1)));
        InExpression inExpression = new InExpression(new Column("biz_line_id"), itemsList);
        AndExpression andExpression = new AndExpression(parenthesis,inExpression);
        plainSelect.setWhere(andExpression);
        System.out.println(plainSelect.toString());
    }
}
