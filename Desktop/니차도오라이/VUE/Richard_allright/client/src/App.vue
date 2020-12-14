<template>
  <v-app>
    <v-container grid-list-md text-xs-center>
      <v-layout row wrap>
        <v-flex xs12>
              <Header />
        
              <v-spacer></v-spacer>

              <v-content> </v-content>
        </v-flex>
      </v-layout>
    </v-container>
</v-app>
</template>

<script>
import Header from "./components/Header.vue";

export default {
  name: "App",
  components: {
    Header
  },
  data: () => ({
    //
  })
};
</script>
