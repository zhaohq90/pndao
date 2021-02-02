package com.piaoniu.pndao.generator.dao;

import com.piaoniu.pndao.annotations.DaoGen;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;

public class DaoEnv {
    private DaoGen daoGen;
    private String daoClassName;
    private String tableName;
    private String createTime;
    private String updateTime;
    Type typeParameter;

    public DaoEnv(DaoGen daoGen,Symbol.ClassSymbol classSymbol, String tablePrefix) {
        this.daoGen = daoGen;
        createTime = daoGen.createTime();
        updateTime = daoGen.updateTime();
        daoClassName = classSymbol.getSimpleName().toString();

        // annotation parameter has higher priority than processor options
        String prefix = "";
        if (daoGen.tablePrefix() != null && !daoGen.tablePrefix().isEmpty()){
            prefix = daoGen.tablePrefix();
        } else if (tablePrefix != null){
            prefix = tablePrefix;
        }

        if (!daoGen.tableName().isEmpty())
            tableName = prefix + daoGen.tableName();
            //"Activity{Dao}"
        else tableName = prefix + daoClassName.subSequence(0,daoClassName.length()-3);
        if (classSymbol.getInterfaces() != null){
            classSymbol.getInterfaces().forEach(i->{
                if (i.getTypeArguments()!=null && !i.getTypeArguments().isEmpty()){
                    if (i.getTypeArguments().size()> 1 || typeParameter != null) throw new RuntimeException("不支持多个类型参数");
                    typeParameter = i.getTypeArguments().get(0);
                }
            });
        }
    }

    public String getTableName() {
        //modify by zhaohq 表名改为小写
        return tableName.toLowerCase();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Symbol.TypeSymbol getRealTypeByTypeParameter(Type type) {
        if (typeParameter == null) throw new RuntimeException("未从类定义中获取类型变量实例,请联系技术支持");
        return typeParameter.tsym;
    }
}
