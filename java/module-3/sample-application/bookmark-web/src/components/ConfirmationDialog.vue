<template>
  <div class="modal-backdrop">
    <div class="modal" role="dialog">
      <section class="modal-body">
          <p>{{$props.message}}</p>
          <div class="button-group">
            <button @click="$emit('ok');" class="btn-primary">{{$props.okButton}}</button>
            <button @click="$emit('cancel');">{{$props.cancelButton}}</button>
          </div>
       </section>
    </div>
  </div>
</template>

<script>

export default {
  props: ['message', 'okButton', 'cancelButton'],
};
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 99;
}

.modal {
  background: #FFFFFF;
  box-shadow: 2px 2px 20px 1px;
  overflow-x: auto;
  display: flex;
  flex-direction: column;
  z-index: 999;
}

.modal-body {
  position: relative;
  padding: 2rem;
}

.modal-body p {
  margin-bottom: 15px;
}

button {
  border: 1px solid black;
  border-radius: 5px;
  padding: 0.25rem 0.5rem;
}

.button-group {
  text-align: center;
}

.btn-primary {
  background-color: rgba(136, 193, 231, 0.4);
}

button:not(:last-child) {
  margin-right: 15px;
}
</style>