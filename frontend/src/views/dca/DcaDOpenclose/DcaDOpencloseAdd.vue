<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="650"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
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
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "DcaDOpencloseAdd",
  props: {
    addVisiable: {
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
      this.dcaDOpenclose = {};
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          this.$post("dcaDOpenclose", {
            ...this.dcaDOpenclose,
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
    setFields() {
      let values = this.form.getFieldsValue(["dcaYear", "gwdj"]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.dcaDOpenclose[_key] = values[_key];
        });
      }
    },
  },
};
</script>
