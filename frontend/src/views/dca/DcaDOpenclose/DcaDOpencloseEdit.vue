<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form">
      <a-form-item v-bind="formItemLayout" label="申报年度">
        <a-input
          placeholder="请输入申报年度"
          v-decorator="[
            'dcaYear',
            { rules: [{ required: true, message: '申报年度不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item v-bind="formItemLayout" label="岗位等级">
        <a-input
          placeholder="请输入岗位等级"
          v-decorator="[
            'gwdj',
            { rules: [{ required: true, message: '岗位等级不能为空' }] },
          ]"
        />
      </a-form-item>
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
      <a-button @click="handleSubmit" type="primary" :loading="loading"
        >提交</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "DcaDOpencloseEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      dcaDOpenclose: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setFormValues({ ...dcaDOpenclose }) {
      let fields = ["dcaYear", "gwdj"];
      let fieldDates = [];
      Object.keys(dcaDOpenclose).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaDOpenclose[key] !== "") {
              obj[key] = moment(dcaDOpenclose[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = dcaDOpenclose[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.dcaDOpenclose.id = dcaDOpenclose.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaDOpenclose = this.form.getFieldsValue();
          dcaDOpenclose.id = this.dcaDOpenclose.id;
          this.$put("dcaDOpenclose", {
            ...dcaDOpenclose,
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
    },
  },
};
</script>
