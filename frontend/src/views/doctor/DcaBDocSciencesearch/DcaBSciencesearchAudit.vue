<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="进院工作以来承担的主要科研项目">
        <div>
          <a-form layout="horizontal">
            <a-row>
              <div>
                <a-col
                  :md="8"
                  :sm="24"
                >
                  <a-form-item
                    label="发薪号/姓名"
                    v-bind="formItemLayout"
                  >
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
              <span style="float: right; margin-top: 3px;">
                <a-button
                  type="primary"
                  @click="exportCustomExcel"
                >导出</a-button>
                <a-button
                  type="primary"
                  @click="search2"
                >查询</a-button>
                <a-button
                  style="margin-left: 8px"
                  @click="reset"
                >重置</a-button>
              </span>
            </a-row>
          </a-form>
        </div>
        <a-tabs
          type="card"
          @change="callback"
        >
          <a-tab-pane
            key="1"
            tab="待审核"
          >
            <a-table
              ref="TableInfo"
              :columns="columns"
              :data-source="dataSource"
              :rowKey="record => record.id"
              :pagination="pagination"
              @change="handleTableChange"
              :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
              :bordered="true"
              :scroll="scroll"
            >
              <template
                slot="projectName"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'projectName')"
                    :value="record.projectName"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="projectType"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'projectType')"
                    :value="record.projectType"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="projectSource"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'projectSource')"
                    :value="record.projectSource"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="contractFund"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'contractFund')"
                    :value="record.contractFund"
                    :precision="2"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="realFund"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'realFund')"
                    :value="record.realFund"
                    :precision="2"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="auditDate2"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'auditDate2')"
                  />
                </div>
              </template>
              <template
                slot="startDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'startDate')"
                  />
                </div>
              </template>
              <template
                slot="endDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'endDate')"
                  />
                </div>
              </template>
              <template
                slot="rankNum"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'rankNum')"
                    :value="record.rankNum"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
               <template
                slot="daoshiRanknum"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'daoshiRanknum')"
                    :value="record.daoshiRanknum"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="isUse"
                slot-scope="text, record"
              >
                <a-checkbox
                  @change="e => onIsUseChange(e,record,'isUse')"
                  :checked="text"
                ></a-checkbox>
              </template>
              <template
                slot="auditSuggestion"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'auditSuggestion')"
                    :value="record.auditSuggestion"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="isBest"
                slot-scope="text, record"
              >
                <div key="jzContent">

                  <a-switch
                    checked-children="是"
                    un-checked-children="否"
                    @change="(e1,f) => inputCheckChange(e1,f,record,'isBest')"
                    :checked="record.isBest=='是'"
                  >
                  </a-switch>
                </div>
              </template>
              <template
                slot="auditTypetp"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-select
                    :value="record.auditTypetp==null?'':record.auditTypetp"
                    style="width: 100%"
                    @change="(e,f) => handleSelectChange(e,f,record,'auditTypetp')"
                  >
                     <a-select-option value="国家自然科学基金面上项目">
              国家自然科学基金面上项目
            </a-select-option>
            <a-select-option value="博士后创新人才支持计划">
              博士后创新人才支持计划
            </a-select-option>
            <a-select-option value="青年科学基金项目">
              青年科学基金项目
            </a-select-option>
            <a-select-option value="中国博士后科学基金-特别项目">
              中国博士后科学基金-特别项目
            </a-select-option>
            <a-select-option value="中国博士后科学基金-面上项目">
              中国博士后科学基金-面上项目
            </a-select-option>
            <a-select-option value="博士后国际交流项目">
              博士后国际交流项目
            </a-select-option>
            <a-select-option value="省级课题">
              省级课题
            </a-select-option>
             <a-select-option value="其他">
              其他
            </a-select-option>
                  </a-select>
                </div>
              </template>
              <template
                slot="auditLb"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-select
                    :value="record.auditLb==null?'':record.auditLb"
                    style="width: 100%"
                    @change="(e,f) => handleSelectChange(e,f,record,'auditLb')"
                  >
                    <a-select-option value="国自基">
                      国自基
                    </a-select-option>
                    <a-select-option value="国重点">
                      国重点
                    </a-select-option>
                    <a-select-option value="国研发">
                      国研发
                    </a-select-option>
                    <a-select-option value="国研发子项">
                      国研发子项
                    </a-select-option>
                    <a-select-option value="部基金">
                      部基金
                    </a-select-option>
                    <a-select-option value="省自基">
                      省自基
                    </a-select-option>
                    <a-select-option value="卫生厅">
                      卫生厅
                    </a-select-option>
                    <a-select-option value="武汉市">
                      武汉市
                    </a-select-option>
                    <a-select-option value="横向">
                      横向
                    </a-select-option>
                    <a-select-option value="省教改">
                      省教改
                    </a-select-option>
                    <a-select-option value="部教改">
                      部教改
                    </a-select-option>
                    <a-select-option value="校级">
                      校级
                    </a-select-option>
                    <a-select-option value="院级">
                      院级
                    </a-select-option>
                     <a-select-option value="厅级">
                      厅级
                    </a-select-option>
                  </a-select>
                </div>
              </template>
              <template
                slot="auditFund"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'auditFund')"
                    :value="record.auditFund"
                    :precision="2"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="auditRank"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'auditRank')"
                    :value="record.auditRank"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="userAccount"
                slot-scope="text, record"
              >
                <a
                  href="#"
                  @click="showUserInfo(text)"
                >{{text}}</a>
              </template>
              <template
                slot="action"
                slot-scope="text, record"
              >
               
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
          <a-tab-pane
            key="2"
            tab="已审核"
            :forceRender="true"
          >
            <dcaBSciencesearch-done
              ref="TableInfo2"
              :state="3"
            >
            </dcaBSciencesearch-done>
          </a-tab-pane>
          <a-tab-pane
            key="3"
            tab="审核未通过"
            :forceRender="true"
          >
            <dcaBSciencesearch-done
              ref="TableInfo3"
              :state="2"
            >
            </dcaBSciencesearch-done>
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
import moment from 'moment';
import DcaBSciencesearchDone from './DcaBSciencesearchDone'
import AuditUserInfo from '../../common/AuditUserDocInfo'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      advanced: false,
      dataSource: [],
      formItemLayout,
      selectedRowKeys: [],
      loading: false,
      dcaBParttimeVisiable: false,
      idNums: 10000,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {
        userAccount: '',
        auditMan: this.dcaYear,
        auditManName: this.dcaType
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 2600,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
      activeKey: 1,
    }
  },
  components: { DcaBSciencesearchDone, AuditUserInfo },
  mounted () {
    this.search()
  },
  props: {
    dcaYear: {
      default: '' //年度
    },
    dcaType: {
      default: '' //中高级
    }
  },
  methods: {
    moment,
     callback (activeKey) {
      this.activeKey = activeKey
    },
    search2 () {
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
      }
      this.search()
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
      this.freshTabs()
    },
    freshTabs () {

      this.$refs.TableInfo2.queryParams.userAccount = this.queryParams.userAccount
      this.$refs.TableInfo2.queryParams.auditMan = this.queryParams.auditMan
      this.$refs.TableInfo2.queryParams.auditManName = this.queryParams.auditManName
      this.$refs.TableInfo3.queryParams.userAccount = this.queryParams.userAccount
      this.$refs.TableInfo3.queryParams.auditMan = this.queryParams.auditMan
      this.$refs.TableInfo3.queryParams.auditManName = this.queryParams.auditManName
      if (this.queryParams.auditXuhaoS !== undefined) {
        this.$refs.TableInfo2.queryParams.auditXuhaoS = this.queryParams.auditXuhaoS
        this.$refs.TableInfo3.queryParams.auditXuhaoS = this.queryParams.auditXuhaoS
      }
      if (this.queryParams.auditXuhaoE !== undefined) {
        this.$refs.TableInfo2.queryParams.auditXuhaoE = this.queryParams.auditXuhaoE
        this.$refs.TableInfo3.queryParams.auditXuhaoE = this.queryParams.auditXuhaoE
      }

      if (this.$refs.TableInfo2.paginationInfo) {
        this.$refs.TableInfo2.paginationInfo.current = 1
      }
      if (this.$refs.TableInfo3.paginationInfo) {
        this.$refs.TableInfo3.paginationInfo.current = 1
      }
       if(this.queryParams.isDeletemark!==undefined && this.queryParams.isDeletemark!=-1){
        this.$refs.TableInfo2.queryParams.isDeletemark = this.queryParams.isDeletemark
        this.$refs.TableInfo3.queryParams.isDeletemark = this.queryParams.isDeletemark
      }
       else{
       delete  this.$refs.TableInfo2.queryParams.isDeletemark 
       delete  this.$refs.TableInfo3.queryParams.isDeletemark 
      }
      this.$refs.TableInfo2.fetch2(this.$refs.TableInfo2.queryParams)
      this.$refs.TableInfo3.fetch2(this.$refs.TableInfo2.queryParams)
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = this.columns
      json.splice(this.columns.length-1,1) //移出第一个
      console.info(json)
      let dataJson = JSON.stringify(json)

      let queryParams= this.queryParams
      
      let state = 1
      if(this.activeKey==1){
         state = 1
      }
       if(this.activeKey==2){
         state = 3
         delete queryParams.auditState
      }
       if(this.activeKey==3){
         state = 2
         delete queryParams.auditState
      }
       if(queryParams.isDeletemark==-1){
        delete queryParams.isDeletemark;
      }
      this.$export('dcaBDocSciencesearch/excel', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        state: state,
        dataJson: dataJson,
        ...queryParams
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列排序规则
      this.sortedInfo = null
      this.paginationInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
    },
    handleSelectChange (v, option, record, filedName) {
      record[filedName] = v
      if(filedName=='auditTypetp'){
         if (v == "国家自然科学基金面上项目") {
          record["auditFund"] = 20;
      }
       if (v == "博士后创新人才支持计划") {
          record["auditFund"] = 20;
      }
      if (v == "青年科学基金项目") {
          record["auditFund"] = 10;
      }
       if (v == "中国博士后科学基金-特别项目") {
          record["auditFund"] = 8;
      }
       if (v == "中国博士后科学基金-面上项目") {
          record["auditFund"] = 5;
      }
       if (v == "博士后国际交流项目") {
          record["auditFund"] = 5;
      }
       if (v == "省级课题") {
          record["auditFund"] = 3;
      }
       if (v == "其他") {
          record["auditFund"] = 0;
      }
      }
    },
    handleChangeState (state) {
      this.queryParams.auditState = state
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      // console.log(selectedRows)

      this.selectedRowKeys = selectedRowKeys

    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    inputCheckChange (blFlag, f, record, filedName) {
      record[filedName] = blFlag ? '是' : '否'
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },


    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    handleAuditNext (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将进入下一个审核人',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBDocSciencesearch/updateNew', {
            jsonStr: jsonStr,
            state: 1,
            auditState: record.auditState
          }).then(() => {
            //this.reset()
            that.$message.success('审核成功')
            that.search()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleAudit (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将审核通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBDocSciencesearch/updateNew', {
            jsonStr: jsonStr,
            state: 3,
            auditState: -1
          }).then(() => {
            //this.reset()
            that.$message.success('审核成功')
            that.search()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleAuditNo (record) {
      let that = this
      this.$confirm({
        title: '确定审核不通过此记录?',
        content: '当您点击确定按钮后，此记录将审核不通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBDocSciencesearch/updateNew', {
            jsonStr: jsonStr,
            state: 2,
            auditState: 0
          }).then(() => {
            //this.reset()
            that.$message.success('操作成功')
            that.search()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    fetch (params = {}) {
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }
      params.sortField = "userAccount"
      params.sortOrder = "descend"
       if(params.isDeletemark==-1){
        delete params.isDeletemark;
      }
      this.loading = true
      this.$get('dcaBDocSciencesearch/audit', {
        ...params,
        state: 1
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    }
  },
  computed: {
    columns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' },
          fixed: 'left'
        },
        
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80,
          fixed: 'left'
        },
        {
          title: '项目名称',
          dataIndex: 'projectName',
          width: 130,
          scopedSlots: { customRender: 'projectName' },
          fixed: 'left'
        },
        {
          title: '项目性质',
          dataIndex: 'projectType',
          width: 130,
          scopedSlots: { customRender: 'projectType' },
          fixed: 'left'
        },
        {
          title: '项目来源',
          dataIndex: 'projectSource',
          width: 130,
          scopedSlots: { customRender: 'projectSource' },
          fixed: 'left'
        },
        {
          title: '合同经费(单位：万)',
          dataIndex: 'contractFund',
          width: 130,
          scopedSlots: { customRender: 'contractFund' },
          fixed: 'left'
        },
        {
          title: '实到经费(单位：万)',
          dataIndex: 'realFund',
          width: 130,
          scopedSlots: { customRender: 'realFund' },
          fixed: 'left'
        },
        {
          title: '批准年月',
          dataIndex: 'auditDate2',
          width: 130,
          scopedSlots: { customRender: 'auditDate2' },
          fixed: 'left'
        },
        {
          title: '起始日期',
          dataIndex: 'startDate',
          width: 130,
          scopedSlots: { customRender: 'startDate' }
        },
        {
          title: '终止日期',
          dataIndex: 'endDate',
          width: 130,
          scopedSlots: { customRender: 'endDate' }
        },
        {
          title: '本人排名',
          dataIndex: 'rankNum',
          width: 130,
          scopedSlots: { customRender: 'rankNum' }
        },
         {
          title: '合作导师排名',
          dataIndex: 'daoshiRanknum',
          width: 130,
          scopedSlots: { customRender: 'daoshiRanknum' }
        },
        
        {
          title: '项目类型',
          dataIndex: 'auditTypetp',
          width: 250,
          scopedSlots: { customRender: 'auditTypetp' },
        
        },
        // {
        //   title: '类别',
        //   dataIndex: 'auditLb',
        //   width: 130,
        //   scopedSlots: { customRender: 'auditLb' },
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        // },
        {
          title: ' 分数',
          dataIndex: 'auditFund',
          width: 130,
          scopedSlots: { customRender: 'auditFund' },
        
        },
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
          title: '状态',
          dataIndex: 'state',
          width: 80,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>
              case 1:
                return <a-tag color="green">已提交</a-tag>
              case 2:
                return <a-tag color="red">审核未通过</a-tag>
              case 3:
                return <a-tag color="#f50">已审核</a-tag>
              default:
                return text
            }
          }
        },
        {
          title: '审核意见',
          dataIndex: 'auditSuggestion',
          scopedSlots: { customRender: 'auditSuggestion' }
        },
        {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }, {
          title: '审核',
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: 180
        }]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
