<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="进院工作以来科研获奖情况">
        <div>
          <a-form layout="horizontal">
            <a-row>
              <div>
                <a-col :md="8" :sm="24">
                  <a-form-item label="发薪号/姓名" v-bind="formItemLayout">
                    <a-input v-model="queryParams.userAccount" />
                  </a-form-item>
                </a-col>
                 <a-col
                  :md="8"
                  :sm="24"
                >
                  <a-form-item
                    label="是否为在站期间"
                    v-bind="formItemLayout"
                  >
                    <a-select v-model="queryParams.isDeletemark" >
                      <a-select-option :value="-1">全部</a-select-option>
                       <a-select-option :value="1">是</a-select-option>
                       <a-select-option :value="2">否</a-select-option>
                    </a-select>
                  </a-form-item>
                </a-col>
              </div>
              <span style="float: right; margin-top: 3px">
                <a-button type="primary" @click="exportCustomExcel"
                  >导出</a-button
                >
                <a-button type="primary" @click="search2">查询</a-button>
                <a-button style="margin-left: 8px" @click="reset"
                  >重置</a-button
                >
              </span>
            </a-row>
          </a-form>
        </div>
        <a-tabs type="card" @change="callback">
          <a-tab-pane key="1" tab="待审核">
            <a-table
              ref="TableInfo"
              :columns="columns"
              :data-source="dataSource"
              :rowKey="(record) => record.id"
              :pagination="pagination"
              @change="handleTableChange"
              :rowSelection="{
                selectedRowKeys: selectedRowKeys,
                onChange: onSelectChange,
              }"
              :bordered="true"
              :scroll="scroll"
            >
              <template slot="spProjectName" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="
                      (e) =>
                        inputChange(e.target.value, record, 'spProjectName')
                    "
                    :value="record.spProjectName"
                  >
                  </a-textarea>
                </div>
              </template>
              <template slot="srProjectGrade" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-select
                    :value="record.srProjectGrade"
                    style="width: 100%"
                    @change="
                      (e, f) =>
                        handleSelectChange(e, f, record, 'srProjectGrade')
                    "
                  >
                    <a-select-option value="国家级"> 国家级 </a-select-option>
                    <a-select-option value="省部级"> 省部级 </a-select-option>
                    <a-select-option value="校级"> 校级 </a-select-option>
                    <a-select-option value="其他"> 其他 </a-select-option>
                  </a-select>
                </div>
              </template>
              <template slot="srProjectLevel" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-select
                    :value="record.srProjectLevel"
                    style="width: 100%"
                    @change="
                      (e, f) =>
                        handleSelectChange(e, f, record, 'srProjectLevel')
                    "
                  >
                    <a-select-option value="一等奖"> 一等奖 </a-select-option>
                    <a-select-option value="二等奖"> 二等奖 </a-select-option>
                    <a-select-option value="三等奖"> 三等奖 </a-select-option>
                    <a-select-option value="其他"> 其他 </a-select-option>
                  </a-select>
                </div>
              </template>
              <template slot="srPrizeDept" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="
                      (e) => inputChange(e.target.value, record, 'srPrizeDept')
                    "
                    :value="record.srPrizeDept"
                  >
                  </a-textarea>
                </div>
              </template>
              <template slot="srPrizeDate" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text == "" || text == null ? "" : text.substr(0, 10) }}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="
                      text == '' || text == null ? '' : moment(text, dateFormat)
                    "
                    @change="
                      (e, f) => handleChange(e, f, record, 'srPrizeDate')
                    "
                  />
                </div>
              </template>
              <template slot="srPrizeRanknum" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="
                      (e) =>
                        inputChange(e.target.value, record, 'srPrizeRanknum')
                    "
                    :value="record.srPrizeRanknum"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template slot="daoshiRanknum" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="
                      (e) =>
                        inputChange(e.target.value, record, 'daoshiRanknum')
                    "
                    :value="record.daoshiRanknum"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template slot="isUse" slot-scope="text, record">
                <a-checkbox
                  @change="(e) => onIsUseChange(e, record, 'isUse')"
                  :checked="text"
                ></a-checkbox>
              </template>
              <template slot="auditSuggestion" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="
                      (e) =>
                        inputChange(e.target.value, record, 'auditSuggestion')
                    "
                    :value="record.auditSuggestion"
                  >
                  </a-textarea>
                </div>
              </template>
              <template slot="isBest" slot-scope="text, record">
                <div key="jzContent">
                  <a-switch
                    checked-children="是"
                    un-checked-children="否"
                    @change="
                      (e1, f) => inputCheckChange(e1, f, record, 'isBest')
                    "
                    :checked="record.isBest == '是'"
                  >
                  </a-switch>
                </div>
              </template>

              <template slot="auditGrade" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-select
                    :value="record.auditGrade == null ? '' : record.auditGrade"
                    style="width: 100%"
                    @change="
                      (e, f) => handleSelectChange(e, f, record, 'auditGrade')
                    "
                  >
                    <a-select-option value="一"> 一 </a-select-option>
                    <a-select-option value="二"> 二 </a-select-option>
                    <a-select-option value="三"> 三 </a-select-option>
                  </a-select>
                </div>
              </template>
              <template slot="auditRank" slot-scope="text, record">
                <div v-if="record.state == 3">
                  {{ text }}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="
                      (e) => inputChange(e.target.value, record, 'auditRank')
                    "
                    :value="record.auditRank"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template slot="userAccount" slot-scope="text, record">
                <a href="#" @click="showUserInfo(text)">{{ text }}</a>
              </template>
              <template slot="action" slot-scope="text, record">
                <a-button
                  v-hasNoPermission="['dca:audit']"
                  type="dashed"
                  block
                  @click="handleAudit(record)"
                >
                  通过
                </a-button>
                <a-button
                  v-hasNoPermission="['dca:audit']"
                  type="danger"
                  block
                  @click="handleAuditNo(record)"
                >
                  审核不通过
                </a-button>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane key="2" tab="已审核" :forceRender="true">
            <dcaBScientificprize-done ref="TableInfo2" :state="3">
            </dcaBScientificprize-done>
          </a-tab-pane>
          <a-tab-pane key="3" tab="审核未通过" :forceRender="true">
            <dcaBScientificprize-done ref="TableInfo3" :state="2">
            </dcaBScientificprize-done>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-spin>
    <audit-userInfo
      ref="userinfo"
      @close="onCloseUserInfo"
      :visibleUserInfo="visibleUserInfo"
      :userAccount="userAccount"
    ></audit-userInfo>
  </div>
</template>

<script>
import moment from "moment";
import DcaBScientificprizeDone from "./DcaBScientificprizeDone";
import AuditUserInfo from "../../common/AuditUserDocInfo";

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 },
};
export default {
  data() {
    return {
      dateFormat: "YYYY-MM-DD",
      advanced: false,
      dataSource: [],
      formItemLayout,
      selectedRowKeys: [],
      loading: false,
      dcaBParttimeVisiable: false,
      idNums: 10000,
      pagination: {
        pageSizeOptions: ["10", "20", "30", "40", "100"],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) =>
          `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`,
      },
      queryParams: {
        userAccount: "",
        auditMan: this.dcaYear,
        auditManName: this.dcaType,
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1800,
        y: window.innerHeight - 200 - 100 - 20 - 80,
      },
      visibleUserInfo: false,
      userAccount: "",
      activeKey: 1,
    };
  },
  components: { DcaBScientificprizeDone, AuditUserInfo },
  mounted() {
    this.search();
  },
  props: {
    dcaYear: {
      default: "", //年度
    },
    dcaType: {
      default: "", //中高级
    },
  },
  methods: {
    moment,
    callback(activeKey) {
      this.activeKey = activeKey;
    },
    exportCustomExcel() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      let json = this.columns;
      json.splice(this.columns.length - 1, 1); //移出第一个
      console.info(json);
      let dataJson = JSON.stringify(json);

      let queryParams = this.queryParams;

      let state = 1;
      if (this.activeKey == 1) {
        state = 1;
      }
      if (this.activeKey == 2) {
        state = 3;
        delete queryParams.auditState;
      }
      if (this.activeKey == 3) {
        state = 2;
        delete queryParams.auditState;
      }
       if(queryParams.isDeletemark==-1){
        delete queryParams.isDeletemark;
      }
      this.$export("dcaBDocScientificprize/excel", {
        sortField: "user_account",
        sortOrder: "ascend",
        state: state,
        dataJson: dataJson,
        ...queryParams,
      });
    },
    search2() {
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
      }
      this.search();
    },
    search() {
      let { sortedInfo } = this;
      let sortField, sortOrder;
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field;
        sortOrder = sortedInfo.order;
      }
      this.fetch({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams,
      });
      this.freshTabs();
    },
    freshTabs() {
      this.$refs.TableInfo2.queryParams.userAccount =
        this.queryParams.userAccount;
      this.$refs.TableInfo2.queryParams.auditMan = this.queryParams.auditMan;
      this.$refs.TableInfo2.queryParams.auditManName =
        this.queryParams.auditManName;
      this.$refs.TableInfo3.queryParams.userAccount =
        this.queryParams.userAccount;
      this.$refs.TableInfo3.queryParams.auditMan = this.queryParams.auditMan;
      this.$refs.TableInfo3.queryParams.auditManName =
        this.queryParams.auditManName;

      if (this.$refs.TableInfo2.paginationInfo) {
        this.$refs.TableInfo2.paginationInfo.current = 1;
      }
      if (this.$refs.TableInfo3.paginationInfo) {
        this.$refs.TableInfo3.paginationInfo.current = 1;
      }
 if(this.queryParams.isDeletemark!==undefined && this.queryParams.isDeletemark!=-1){
        this.$refs.TableInfo2.queryParams.isDeletemark = this.queryParams.isDeletemark
        this.$refs.TableInfo3.queryParams.isDeletemark = this.queryParams.isDeletemark
        
      }
      else{
       delete  this.$refs.TableInfo2.queryParams.isDeletemark 
       delete  this.$refs.TableInfo3.queryParams.isDeletemark 
      }
      this.$refs.TableInfo2.fetch2(this.$refs.TableInfo2.queryParams);
      this.$refs.TableInfo3.fetch2(this.$refs.TableInfo2.queryParams);
    },
    reset() {
      // 取消选中
      this.selectedRowKeys = [];
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent;
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent;
        this.paginationInfo.pageSize = this.pagination.defaultPageSize;
      }
      // 重置列排序规则
      this.sortedInfo = null;
      this.paginationInfo = null;
      // 重置查询参数
      this.queryParams = {};
      this.fetch();
    },
    handleTableChange(pagination, filters, sorter) {
      this.sortedInfo = sorter;
      this.paginationInfo = pagination;
      this.fetch({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams,
      });
    },
    handleSelectChange(v, f, record, filedName) {
      record[filedName] = v;
      record["auditName"] = this.calcFenShu(
        record["srProjectGrade"],
        record["srProjectLevel"],
        record["srPrizeRanknum"]
      );
    },
    showUserInfo(text) {
      //debugger
      this.visibleUserInfo = true;
      this.userAccount = text;
    },

    onCloseUserInfo() {
      this.visibleUserInfo = false;
    },
    onSelectChange(selectedRowKeys, selectedRows) {
      // console.log(selectedRows)

      this.selectedRowKeys = selectedRowKeys;
    },
    handleChange(date, dateStr, record, filedName) {
      const value = dateStr;
      record[filedName] = value;
    },
    handleChangeState(state) {
      this.queryParams.auditState = state;
    },
    inputCheckChange(blFlag, f, record, filedName) {
      record[filedName] = blFlag ? "是" : "否";
    },
    inputChange(value, record, filedName) {
      record[filedName] = value;
      if (filedName == "srPrizeRanknum") {
        record["auditName"] = this.calcFenShu(
          record["srProjectGrade"],
          record["srProjectLevel"],
          record["srPrizeRanknum"]
        );
      }
    },
    onIsUseChange(e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    calcFenShu(jb, dj, rank) {
      let fs = "0";
      if (rank == 1) {
        if (jb == "国家级" || jb == "省部级") {
          if (dj == "一等奖") {
            fs = "10";
          }
          if (dj == "二等奖") {
            fs = "5";
          }
          if (dj == "三等奖") {
            fs = "3";
          }
        }
      }
      return fs;
    },
    handleAuditNext(record) {
      let that = this;
      this.$confirm({
        title: "确定审核通过此记录?",
        content: "当您点击确定按钮后，此记录将进入下一个审核人",
        centered: true,
        onOk() {
          let jsonStr = JSON.stringify(record);
          that.loading = true;
          that
            .$post("dcaBDocScientificprize/updateNew", {
              jsonStr: jsonStr,
              state: 1,
              auditState: record.auditState,
            })
            .then(() => {
              //this.reset()
              that.$message.success("审核成功");
              that.search();
              that.loading = false;
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    handleAudit(record) {
      let that = this;
      this.$confirm({
        title: "确定审核通过此记录?",
        content: "当您点击确定按钮后，此记录将审核通过",
        centered: true,
        onOk() {
          let jsonStr = JSON.stringify(record);
          that.loading = true;
          that
            .$post("dcaBDocScientificprize/updateNew", {
              jsonStr: jsonStr,
              state: 3,
              auditState: -1,
            })
            .then(() => {
              //this.reset()
              that.$message.success("审核成功");
              that.search();
              that.loading = false;
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    handleAuditNo(record) {
      let that = this;
      this.$confirm({
        title: "确定审核不通过此记录?",
        content: "当您点击确定按钮后，此记录将审核不通过",
        centered: true,
        onOk() {
          let jsonStr = JSON.stringify(record);
          that.loading = true;
          that
            .$post("dcaBDocScientificprize/updateNew", {
              jsonStr: jsonStr,
              state: 2,
              auditState: 0,
            })
            .then(() => {
              //this.reset()
              that.$message.success("操作成功");
              that.search();
              that.loading = false;
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    fetch(params = {}) {
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current;
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize;
        params.pageSize = this.paginationInfo.pageSize;
        params.pageNum = this.paginationInfo.current;
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize;
        params.pageNum = this.pagination.defaultCurrent;
      }
       if(params.isDeletemark==-1){
        delete params.isDeletemark;
      }
      params.sortField = "userAccount";
      params.sortOrder = "descend";
      this.loading = true;
      this.$get("dcaBDocScientificprize/audit", {
        ...params,
        state: 1,
      }).then((r) => {
        let data = r.data;
        this.loading = false;
        const pagination = { ...this.pagination };
        pagination.total = data.total;
        this.dataSource = data.rows;
        this.pagination = pagination;
      });
    },
  },
  computed: {
    columns() {
      return [
        {
          title: "发薪号",
          dataIndex: "userAccount",
          width: 80,
          scopedSlots: { customRender: "userAccount" },
          fixed: "left",
        },
        {
          title: "姓名",
          dataIndex: "userAccountName",
          width: 80,
          fixed: "left",
        },
        {
          title: "获奖项目名称",
          dataIndex: "spProjectName",
          width: 130,
          scopedSlots: { customRender: "spProjectName" },
          fixed: "left",
        },
        {
          title: "奖项级别",
          dataIndex: "srProjectGrade",
          width: 130,
          scopedSlots: { customRender: "srProjectGrade" },
        },
        {
          title: "奖项等级",
          dataIndex: "srProjectLevel",
          width: 130,
          scopedSlots: { customRender: "srProjectLevel" },
        },
        {
          title: "授奖部门",
          dataIndex: "srPrizeDept",
          width: 130,
          scopedSlots: { customRender: "srPrizeDept" },
        },
        {
          title: "批准年月",
          dataIndex: "srPrizeDate",
          width: 130,
          scopedSlots: { customRender: "srPrizeDate" },
        },
        {
          title: "本人排名",
          dataIndex: "srPrizeRanknum",
          width: 130,
          scopedSlots: { customRender: "srPrizeRanknum" },
        },
        {
          title: "合作导师排名",
          dataIndex: "daoshiRanknum",
          width: 130,
          scopedSlots: { customRender: "daoshiRanknum" },
        },
        {
          title: "分数",
          dataIndex: "auditName",
          width: 80,
        },
        // {
        //   title: '等级',
        //   dataIndex: 'auditGrade',
        //   width: 130,
        //   scopedSlots: { customRender: 'auditGrade' },
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        // },
        // {
        //   title: '排名',
        //   dataIndex: 'auditRank',
        //   width: 130,
        //   scopedSlots: { customRender: 'auditRank' },
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        // },
        {
          title: "状态",
          dataIndex: "state",
          width: 80,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>;
              case 1:
                return <a-tag color="green">已提交</a-tag>;
              case 2:
                return <a-tag color="red">审核未通过</a-tag>;
              case 3:
                return <a-tag color="#f50">已审核</a-tag>;
              default:
                return text;
            }
          },
        },
        {
          title: "审核意见",
          dataIndex: "auditSuggestion",
          scopedSlots: { customRender: "auditSuggestion" },
        },
        {
          title: "附件",
          dataIndex: "fileId",
          customRender: (text, row, index) => {
            if (text != null && text != "") {
              return (
                <a href={row.fileUrl} target="_blank">
                  查看
                </a>
              );
            }
            return "";
          },
          width: 80,
        },
        {
          title: "审核",
          key: "action",
          scopedSlots: { customRender: "action" },
          width: 180,
        },
      ];
    },
  },
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
