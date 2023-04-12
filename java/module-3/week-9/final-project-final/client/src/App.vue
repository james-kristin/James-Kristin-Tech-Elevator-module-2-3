<template>
  <div id="app">
    <header>
      <img
        id="logo-image"
        src="https://via.placeholder.com/400x70/99eea8/aaaaaa?text=Your+Logo+here..."
        alt="Your logo here"
      />
    </header>
    <nav>
      <div id="nav-left">
        <router-link v-bind:to="{ name: 'products' }">Home</router-link>
        <router-link
          v-bind:to="{ name: 'cart' }"
          v-if="$store.state.token != ''"
          >Cart</router-link
        >
      </div>
      <div
        id="message-bar"
        v-bind:class="'message-' + $store.state.message.level"
        v-bind:title="$store.state.message.text"
      >
        <font-awesome-icon
          class="dismiss-message-icon"
          icon="fa-solid fa-xmark"
          title="Dismiss message"
          v-if="$store.state.message.text"
          v-on:click="$store.commit('CLEAR_MESSAGE')"
        />
        {{ $store.state.message.text }}
      </div>
      <div id="nav-right">
        <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token"
          >Logout</router-link
        >
        <router-link v-bind:to="{ name: 'login' }" v-else>Login</router-link>
      </div>
    </nav>
    <main>
      <router-view />
    </main>
    <footer>&copy; 2022. All rights reserved.</footer>
  </div>
</template>

<style scoped>
#app {
  height: 100%;
  display: grid;
  grid-template-rows: 80px 25px 1fr 25px;
  grid-template-areas:
    "header"
    "nav"
    "main"
    "footer";
}

header {
  display: flex;
  align-items: center;
  justify-content: center;
  grid-area: header;
  background-color: #bbb;
  font-size: 2rem;
  padding: 0 20px;
}

#logo-image {
  border-radius: 6px;
  box-shadow: 2px 2px green;
}

nav {
  grid-area: nav;
  display: grid;
  grid-template-columns: auto 1fr auto;
  background-color: bisque;
  border-top: 1px solid #777;
  border-bottom: 2px solid #777;
}

nav {
  background-color: rgb(91, 158, 247);
}
nav a {
  text-decoration: none;
  color: white;
  padding: 2px 3px;
}

#nav-left {
  padding: 0 10px;
}

#nav-left > a {
  padding: 0 20px;
}

#nav-right {
  padding: 0 10px;
}
#nav-right > a {
  padding: 0 20px;
}

nav > a:last-child {
  align-self: flex-end;
}

nav a.router-link-exact-active {
  background-color: rgba(0, 0, 0, 0.3);
}

main {
  grid-area: main;
  padding: 5px 20px;
  background: linear-gradient(
    rgba(153, 238, 168, 0.3),
    rgba(91, 158, 247, 0.3)
  );
  overflow: auto;
}

footer {
  grid-area: footer;
  display: flex;
  flex-direction: row;
  align-items: center;
  font-size: 0.8rem;
  padding: 0 10px;
  border-top: 2px solid #777;
  background-color: rgb(91, 158, 247);
}

#message-bar {
  padding: 0 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  /* width: 100%; */
}
.dismiss-message-icon {
  font-size: 1.1rem;
  background-color: rgba(0,0,0,0.15);
  padding: 1px 3px;
  cursor: pointer;
}
.message-S {
  background-color: lightgreen;
}
.message-W {
  background-color: rgb(225, 226, 113);
}
.message-E {
  background-color: rgba(247, 126, 126);
  border-right: 1px solid red;
}
</style>