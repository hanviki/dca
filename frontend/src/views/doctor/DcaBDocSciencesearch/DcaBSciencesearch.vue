<template>
  <a-card class="card-area" title="进院工作以来承担的主要科研项目">
    <div>
      <a-button
        @click="handleAdd"
        type="primary"
        :loading="loading"
      >添加行</a-button>
      <a-button
        @click="handleDelete"
        type="primary"
        :loading="loading"
      >删除行</a-button>
    </div>
    <a-table
      :columns="columns"
      :data-source="dataSource"
      :rowKey="record => record.id"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      bordered
      :scroll="scroll"
    >
     
      <template
        slot="projectName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
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
        <div v-if="record.state==3 || record.state==1">
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
        <div v-if="record.state==3 || record.state==1">
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
        <div v-if="record.state==3 || record.state==1">
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
        <div v-if="record.state==3 || record.state==1">
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
        <div v-if="record.state==3 || record.state==1">
          {{text=="" || text==null?"":text.substr(0,10)}}
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
        <div v-if="record.state==3 || record.state==1">
          {{text=="" || text==null?"":text.substr(0,10)}}
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
        <div v-if="record.state==3 || record.state==1">
          {{text=="" || text==null?"":text.substr(0,10)}}
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
        <div v-if="record.state==3 || record.state==1">
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
        <div v-if="record.state==3 || record.state==1">
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
        slot="fileId"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          <a
            :href="record.fileUrl"
            v-if="text!=null && text !=''"
            target="_blank"
          >查看</a>
        </div>
        <div v-else>
          <a-button
            type="dashed"
            block
            @click="OpenFile(record)"
          >
             {{record.fileId!=null &&record.fileId !=''?'已上传':'上传' }}
          </a-button>
        </div>
      </template>
       <template
        slot="auditTypetp"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-select
            :value="record.auditTypetp"
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
    </a-table>
    <div>
      <a-button
        @click="handleSave"
        type="primary"
        :loading="loading"
      >保存草稿</a-button>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
    <tableUpload-file
      ref="upFile"
      :fileId="editRecord.fileId"
      :fileVisiable="fileVisiable"
      @setFileId="setFileId"
    >
    </tableUpload-file>
  </a-card>
</template>

<script>
import moment from 'moment';
import TableUploadFile from '../../common/TableUploadFile'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      CustomVisiable: false,
      idNums: 10000,
      fileVisiable: false,
      editRecord: {
        fileId: ''
      },
       scroll: {
        x: 2400,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
    }
  },
  components: { TableUploadFile },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    showFile (record) {
      window.location.href = record.fileUrl
    },
    OpenFile (record) {
      this.editRecord = record
      this.fileVisiable = true
      if (record.fileId != undefined && record.fileId != '') {
        this.$refs.upFile.fetch(record.fileId)
      }
    },
    setFileId (fileId, fileUrl) {
      this.fileVisiable = false
      console.log(fileUrl)
      /**
       const dataSource = [...this.dataSource]
       console.log(this.editRecord.id)
       let record=dataSource.filter(p=>p.id===this.editRecord.id)
       console.log(record)*/
      this.editRecord["fileId"] = fileId
      this.editRecord["fileUrl"] = fileUrl
      //this.dataSource =[...dataSource]
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      if (selectedRows.length > 0) {
        if (selectedRows[0].state != 3 && selectedRows[0].state != 1) {
          this.selectedRowKeys = selectedRowKeys
        }
      }
      else{
        this.selectedRowKeys = selectedRowKeys
      }
    },
    handleSelectChange(v, f, record, filedName) {
      record[filedName] = v;
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
    
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleAdd () {
      for (let i = 0; i < 4; i++) {
        this.dataSource.push({
          id: (this.idNums + i + 1).toString(),
          state: 0,
          fileId: '',
          fileUrl: '',
          projectName: '',
          projectType: '',
          projectSource: '',
          contractFund: '',
          realFund: '',
          auditDate2: '',
          startDate: '',
          endDate: '',
          rankNum: '',
          daoshiRanknum: '',
          isUse: false,
          auditTypetp: '',
          auditFund: ''
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSourceAll = [...this.dataSource]
      const dataSource = dataSourceAll.filter(p=>p.state==0 ||p.state==2)
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.fileId != '' || element.fileUrl != '' || element.projectName != '' || element.projectType != '' || element.projectSource != '' || element.contractFund != '' || element.realFund != '' || element.auditDate2 != '' || element.startDate != '' || element.endDate != '' || element.rankNum != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length < 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBDocSciencesearch/addNew', {
          jsonStr: jsonStr,
          state: 0
        }).then(() => {
          // this.reset()
          this.$message.success('保存成功')
          this.fetch()
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      }
    },
    handleSubmit () {
      let that = this
      this.$confirm({
        title: '确定提交全部记录?',
        content: '当您点击确定按钮后，信息将不能修改',
        centered: true,
        onOk () {
          const dataSourceAll = [...that.dataSource]
          const dataSource = dataSourceAll.filter(p=>p.state==0 ||p.state==2)
          let dataAdd = []
          let msg= ""
          dataSource.forEach(element => {
           
            if (element.fileId != '' || element.fileUrl != '' || element.projectName != '' || element.projectType != '' || element.projectSource != '' || element.contractFund != '' || element.realFund != '' || element.auditDate2 != '' || element.startDate != '' || element.endDate != '' || element.rankNum != '') {
               if(element.projectName == ''){
              msg+="项目名称必须填写"
            }
            if(element.projectType == ''){
              msg+="项目性质必须填写"
            }
             if(element.projectSource == ''){
              msg+="项目来源必须填写"
            }
             if(element.contractFund == ''){
              msg+="合同经费必须填写"
            }
             if(element.auditDate2 == ''){
              msg+="批准年月必须填写"
            }
             if(element.startDate == ''){
              msg+="起始时间必须填写"
            }
             if(element.endDate == ''){
              msg+="结束时间必须填写"
            }
             if(element.rankNum == ''){
              msg+="本人排名必须填写"
            }
              dataAdd.push(element)
            }
          });
          if (dataAdd.length < 0 ||msg!='') {
            if(msg!=''){
              that.$message.warning(msg)
            }
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBDocSciencesearch/addNew', {
              jsonStr: jsonStr,
              state: 1
            }).then(() => {
              //this.reset()
              that.$message.success('提交成功')
               that.fetch()
              that.CustomVisiable = false //提交之后 不能再修改
              that.loading = false
            }).catch(() => {
              that.loading = false
            })
          }
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })


    },
    handleDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let dcaBPatentIds = that.selectedRowKeys.join(',')
          const dataSource = [...that.dataSource];
          let new_dataSource = dataSource.filter(p => that.selectedRowKeys.indexOf(p.id) < 0)
          that.dataSource = new_dataSource
          that.$message.success('删除成功')
          that.selectedRowKeys = []
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    fetch () {
      this.$get('dcaBDocSciencesearch/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows

        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            state: 0,
            fileId: '',
            fileUrl: '',
            projectName: '',
            projectType: '',
            projectSource: '',
            contractFund: '',
            realFund: '',
            auditDate2: '',
            startDate: '',
            endDate: '',
            rankNum: '',
            daoshiRanknum: '',
            isUse: false,
            auditTypetp: '',
            auditFund: ''
          })
          this.idNums = this.idNums + 4
        }
      })
    }
  },
  computed: {
    columns () {
      return [{
        title: '项目名称',
        dataIndex: 'projectName',
        width: 200,
        scopedSlots: { customRender: 'projectName' }
      },
      {
        title: '项目性质',
        dataIndex: 'projectType',
        width: 130,
        scopedSlots: { customRender: 'projectType' }
      },
      {
        title: '项目来源',
        dataIndex: 'projectSource',
        width: 130,
        scopedSlots: { customRender: 'projectSource' }
      },
       {
          title: '项目类型',
          dataIndex: 'auditTypetp',
          width: 250,
          scopedSlots: { customRender: 'auditTypetp' },
         
        },
       
        {
          title: ' 分数',
          dataIndex: 'auditFund',
          width: 130,
          scopedSlots: { customRender: 'auditFund' },
        
        },
      {
        title: '合同经费(单位：万)',
        dataIndex: 'contractFund',
        width: 130,
        scopedSlots: { customRender: 'contractFund' }
      },
      {
        title: '实到经费(单位：万)',
        dataIndex: 'realFund',
        width: 130,
        scopedSlots: { customRender: 'realFund' }
      },
      {
        title: '批准年月',
        dataIndex: 'auditDate2',
        width: 130,
        scopedSlots: { customRender: 'auditDate2' }
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
        title: '状态',
        dataIndex: 'state',
        width: 100,
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
        dataIndex: 'auditSuggestion'
      },
     
      {
        title: '附件',
        dataIndex: 'fileId',
        scopedSlots: { customRender: 'fileId' },
        width: 80
      }]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
