<template>
  <a-drawer
    title="职称申报"
    :maskClosable="false"
    width="800"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="申报年度">
        <a-select
          style="width: 200px"
          @change="handleChange"
          v-decorator="[
            'hk3',
            { rules: [{ required: true, message: '请输入申报年度' }] },
          ]"
        >
          <a-select-option v-for="d in yearArr" :key="d">
            {{ d }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="岗位等级">
        <a-select
          show-search
          style="width: 200px"
          @change="handleChangezw"
          v-decorator="[
            'hk2',
            { rules: [{ required: true, message: '请输入岗位等级' }] },
          ]"
        >
         <a-select-option v-for="d in arrDj" :key="d.value">
            {{ d.text }}
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="申报职称">
        <a-select
          show-search
          style="width: 200px"
          @change="handleChangezc"
          v-decorator="[
            'hk4',
            { rules: [{ required: true, message: '请输入申报职称' }] },
          ]"
        >
          <a-select-option v-for="d in zc" :key="d.value">
            {{ d.text }}
          </a-select-option>
        </a-select>
      </a-form-item>
    
       <div style="height: 300px; overflow: auto">
        <yj-tree ref="yjTree" v-show="yjShow"> </yj-tree>
      </div> 
    </a-form>

    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: 0.8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmitTotal" type="primary" :loading="loading"
        >提交</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import YjTree from "./YjTree";
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15 },
};
export default {
  name: "DcaBUserapplyAdd",
  props: {
    addVisiable: {
      default: false,
    },
  },
  components: { YjTree },
  data() {
    return {
      current: {
        default: 0,
      },
      loading: false,
      yjShow: false,
      yjDj: "三级",
      formItemLayout,
      form: this.$form.createForm(this),
      dcaBUserapply: {},
      dcaYear: "",
      npPositionName: "",
      gwdj: "",
      zc: "",
      arrDj: [ ],
      yjData: {},
      zj: [
        {
          value: "主治医师",
          text: "主治医师",
        },
        {
          value: "主管药师",
          text: "主管药师",
        },
        {
          value: "主管护师",
          text: "主管护师",
        },
        {
          value: "主管技师",
          text: "主管技师",
        },
        {
          value: "编辑",
          text: "编辑",
        },
        {
          value: "工程师",
          text: "工程师",
        },
        {
          value: "馆员",
          text: "馆员",
        },
        {
          value: "会计师",
          text: "会计师",
        },
        {
          value: "经济师",
          text: "经济师",
        },
        {
          value: "审计师",
          text: "审计师",
        },
        {
          value: "助理研究员",
          text: "助理研究员",
        },
      ],
      cj: [
        {
          value: "住院医师",
          text: "住院医师",
        },
        {
          value: "药师",
          text: "药师",
        },
        {
          value: "护师",
          text: "护师",
        },
        {
          value: "技师",
          text: "技师",
        },
        {
          value: "图书管理员",
          text: "图书管理员",
        },
        {
          value: "会计员",
          text: "会计员",
        },
        {
          value: "助理编辑",
          text: "助理编辑",
        },
        {
          value: "助理工程师",
          text: "助理工程师",
        },
        {
          value: "助理馆员",
          text: "助理馆员",
        },
        {
          value: "助理会计师",
          text: "助理会计师",
        },
      ],
      j23: [
        {
          value: "二级",
          text: "二级",
        },
        {
          value: "三级",
          text: "三级",
        },
      ],
      zg: [
        {
          value: "教授主任医师",
          text: "教授主任医师",
        },
        {
          value: "教授",
          text: "教授",
        },
        {
          value: "主任医师",
          text: "主任医师",
        },
        {
          value: "研究员",
          text: "研究员",
        },
        {
          value: "主任护师",
          text: "主任护师",
        },
        {
          value: "主任技师",
          text: "主任技师",
        },
        {
          value: "主任药师",
          text: "主任药师",
        },
        {
          value: "教授级高级工程师",
          text: "教授级高级工程师",
        },
        {
          value: "编审",
          text: "编审",
        },
      ],
      fg: [
        {
          value: "副教授副主任医师",
          text: "副教授副主任医师",
        },
        {
          value: "副教授",
          text: "副教授",
        },
        {
          value: "副主任医师",
          text: "副主任医师",
        },
        {
          value: "副研究员",
          text: "副研究员",
        },
        {
          value: "副主任护师",
          text: "副主任护师",
        },
        {
          value: "副主任技师",
          text: "副主任技师",
        },
        {
          value: "副主任药师",
          text: "副主任药师",
        },
        {
          value: "高级工程师",
          text: "高级工程师",
        },
        {
          value: "副编审",
          text: "副编审",
        },
      ],
      dcaYears: [],
      yearArr: []
    };
  },
  watch: {
      addVisiable (){
        if(this.addVisiable){
          this.fetchDcaYear()
        }
      }
  },
  computed: {
    // yearArr() {
    //   let arr = [];
    //   for(let val in this.dataYears ){
    //      arr.push({
    //       value: val.dcaYear, text: val.dcaYear
    //      })
    //   }
    //   // var myDate = new Date()
    //   // var startYear = myDate.getFullYear() - 2//起始年份
    //   // var endYear = myDate.getFullYear() + 1//结束年份
    //   // for (var i = startYear; i <= endYear; i++) {
    //   //   arr.push({ value: i, text: i })
    //   // }
    //   return arr;
    // },
  },
  methods: {
    reset() {
      this.loading = false;
      this.yjShow = false;
      this.dcaBUserapply = {};
      this.yearArr= []
      this.arrDj= []
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    fetchDcaYear (){
         this.$get('dcaDOpenclose', {
        state: 0
      }).then((r) => {
        let data = r.data
        this.dcaYears = data.rows
         data.rows.forEach((val) => {
          if (this.yearArr.indexOf(val.dcaYear) == -1) {
            this.yearArr.push(val.dcaYear);
            
          }
        });
      });
    },
    handleChange(value) {
      console.info(2222)
      this.dcaYear = value;
      this.gwdj='';
       this.form.getFieldDecorator("hk2");
      this.form.setFieldsValue({
        hk2: ''
      });
      // if(this.dcaYear=='2022'){
      //   this.arrDj =[{text: '正高',value: '正高' },{text: '副高',value: '副高'}];
      // }
      this.arrDj = []
      // if(this.dcaYear=='2022'){
      //   this.arrDj =[{text: '中级',value: '中级' },{text: '初级',value: '初级' }];
      // }
      // else {
      //   this.arrDj = [{text: '正高', value: '正高'} ,{text: '副高', value: '副高'} ,{text: '二三级', value: '二三级'} ];
      // }

       let dataYear= this.dcaYears.filter(p=>p.dcaYear==value);
      
      dataYear.forEach(val=>{
        console.info(val)
         if(val.gwdj=='高级'){
           this.arrDj.push({
               text: '正高', value: '正高'
           })
           this.arrDj.push({
               text: '副高', value: '副高'
           })
         }
         if(val.gwdj=='中初级'){
           this.arrDj.push({
               text: '中级', value: '中级'
           })
           this.arrDj.push({
               text: '初级', value: '初级'
           })
         }
         if(val.gwdj=='二三级'){
           this.arrDj.push({
               text: '二三级', value: '二三级'
           })
         }
       });
    },
    handleChangezc(value) {
     this.yjShow = false;
      this.npPositionName = value;
      if (value == "二级" || value == "三级") {
         this.yjShow = true;
        this.$get("dcaDYj/tree", { dj: value }).then((r) => {
          this.$refs.yjTree.menuTreeData = r.data.rows.children;
          this.$refs.yjTree.allTreeKeys = r.data.ids;
        });
      }
      // this.$refs.yjTree.allTreeKeys = r.data.ids
    },
    handleChangezw(value) {
      this.yjShow = false;
      if (value == "正高") {
        this.zc = this.zg;
      }
      if (value == "副高") {
        this.zc = this.fg;
      }
      if (value == "中级") {
        this.zc = this.zj;
      }
      if (value == "初级") {
        this.zc = this.cj;
      }
      if (value == "二三级") {
        this.zc = this.j23;
      }
      this.gwdj = value;
      this.form.getFieldDecorator("hk4");
      this.form.setFieldsValue({
        hk4: ''
      });
      this.npPositionName= '';
    },
    handleSubmitTotal() {
      // if(this.gwdj=='正高'||this.gwdj=='副高'){
      //   this.handleSubmit();
      // }
      if(this.gwdj=='二三级'){
        this.handleSubmit23();
      }
      else{
        this.handleSubmit();
      }
    },
    handleSubmit23() {
      var yjIds = this.$refs.yjTree.getAuditKey();
      if (yjIds == "") {
        this.$message.warning("请必须选择下述条件中的一项进行提交");
        return;
      } else {
        this.form.validateFields((err, values) => {
          if (!err) {
            this.$post("dcaBUserapply", {
              dcaYear: this.dcaYear,
              gwdj: this.gwdj,
              npPositionName: this.npPositionName,
              deleFlag: '1',
              yjIDs: yjIds, //二三级
            })
              .then(() => {
                this.reset();
                this.$emit("success");
              })
              .catch(() => {
                this.loading = false;
              });
          }
        });
       }
    },
    handleSubmit() {
      // var yjIds = this.$refs.yjTree.getAuditKey();
      // if (yjIds == "") {
      //   this.$message.warning("请必须选择下述条件中的一项进行提交");
      //   return;
      // } else {
        this.form.validateFields((err, values) => {
          if (!err) {
            this.$post("dcaBUserapply", {
              dcaYear: this.dcaYear,
              gwdj: this.gwdj,
              npPositionName: this.npPositionName,
              deleFlag: '1',
            //  yjIDs: yjIds, //二三级
            })
              .then(() => {
                this.reset();
                this.$emit("success");
              })
              .catch(() => {
                this.loading = false;
              });
          }
        });
      // }
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "ks",
        "xl",
        "sexName",
        "birthday",
        "telephone",
        "zyjsgw",
        "appointedDate",
        "npPositionName",
        "gwdj",
        "deptName",
        "positionName",
        "schoolDate",
        "xcszyjzc",
        "dcaYear",
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.dcaBUserapply[_key] = values[_key];
        });
      }
    },
  },
};
</script>
