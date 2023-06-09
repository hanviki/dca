<template>
  <div>
    <a-spin :spinning="loading">
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        bordered
        :scroll="scroll"
      >
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
                  type="dashed"
                  block
                  @click="handleAuditNext(record)"
                >
                  退回待审核
                </a-button>
              </template>
      </a-table>
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
import AuditUserInfo from '../../common/AuditUserDocInfo'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      loading: false,
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
        auditMan: '',
        auditManName: ''
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1800,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: ''
    }
  },
  props: {
    state: {
      default: 3
    }
  },
  components: { AuditUserInfo },
  mounted () {
    //this.fetch2()
  },
  methods: {
    moment,
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
        title: '确定退回此记录?',
        content: '当您点击确定按钮后，此记录将退回待审核',
        centered: true,
        onOk () {
          record.auditState = 0
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBDocScientificprize/updateNew', {
            jsonStr: jsonStr,
            state: 1,
            auditState: -1
          }).then(() => {
            //this.reset()
            that.$message.success('保存成功')
            that.fetch2(that.queryParams)
           // that.freshTabs()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    fetch2 (params = {}) {
      this.loading = true
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
       if(params.isDeletemark==-1){
        delete params.isDeletemark;
      }
      params.sortField = "userAccount"
      params.sortOrder = "descend"
      this.$get('dcaBDocScientificprize/audit', {
        state: this.state,
        ...params
      }).then((r) => {
        this.loading = false
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    },
    fetch (userAccount) {
      this.loading = true
      this.queryParams.userAccount = userAccount
      let params = {}
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = 1
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = 1
      }
      params.sortField = "userAccount"
      params.sortOrder = "descend"
      params.userAccount = userAccount
      this.$get('dcaBDocScientificprize/audit', {
        state: this.state,
        ...params
      }).then((r) => {
        this.loading = false
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
    },
  },
  computed: {
    columns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' }
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '获奖项目名称',
          dataIndex: 'spProjectName',
          width: 130
        },
        {
          title: '奖项级别',
          dataIndex: 'srProjectGrade',
          width: 130
        },
        {
          title: '奖项等级',
          dataIndex: 'srProjectLevel',
          width: 130
        },
        {
          title: '授奖部门',
          dataIndex: 'srPrizeDept',
          width: 130
        },
        {
          title: '批准年月',
          dataIndex: 'srPrizeDate',
          width: 130,
          customRender: (text, row, index) => {
            if(text==null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '本人排名',
          dataIndex: 'srPrizeRanknum',
          width: 130
        },
         {
        title: '合作导师排名',
        dataIndex: 'daoshiRanknum',
        width: 130
      },
        {
          title: '分数',
          dataIndex: 'auditName',
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
          width: 150
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>