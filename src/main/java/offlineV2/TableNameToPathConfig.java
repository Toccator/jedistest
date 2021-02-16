package offlineV2;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public enum  TableNameToPathConfig {

    USER_INFO("cif_user_info","cif_user_info"),
    EBANK_LOGIN("pjnl_ebank_login","pjnl_ebank_loin"),
    EBANK_TRANSFER("pjnl_ebank_login","pjnl_ebank_login"),
    PAY("pjnl_transfer","pjin_ebank_transfer");

    public String tableName;

    public String tablePath;

    TableNameToPathConfig(String tableName,String tablePath){
        this.tableName = tableName;
        this.tablePath = tablePath;
    }


    public String getTableName() {
        return tableName;
    }

    public List<String> getTable(String hdfsPrefix,List<String> partitions){
        return partitions.stream().map(a -> String.format("%s/%s/month=%s",hdfsPrefix,tableName,a)).collect(Collectors.toList());
    }



}
