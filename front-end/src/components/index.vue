<template>
  <div>
    <h2>List of all pizzas:</h2>

    <div>
      <label for="search">Search by Name:</label>
      <input type="text" v-model="searchQuery" @input="searchPizzas">
    </div>

    <ul>
      <li v-for="photo in filteredPhotos" :key="photo.id">
        <router-link :to="{ name: 'photo-detail', params: { id: photo.id } }">
          {{ photo.title }}
        </router-link>
        <button @click="deletePhoto(photo.id)">Delete</button>
      </li>
    </ul>

    <!-- <router-link :to="{ name: 'CreatePizza' }">Create a new Pizza bro!</router-link> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      photos: [],
      searchQuery: '',
    };
  },
  computed: {
    filteredPhotos() {
      return this.photos.filter(photo => photo.title.toLowerCase().includes(this.searchQuery.toLowerCase()));
    }
  },
  mounted() {
    this.fetchPhotos();
  },
  methods: {
    async fetchPhotos() {
      try {
        const response = await fetch('http://localhost:8080/api/photos');
        const photos = await response.json();
        this.photos = photos;
      } catch (error) {
        console.error(error);
      }
    },
    async deletePhoto(photoId) {
      try {
        const response = await fetch(`http://localhost:8080/api/photos/${photoId}`, {
          method: 'DELETE',
        });

        if (response.ok) {
          this.fetchPhotos();
        } else {
          console.error('Failed to delete photo.');
        }
      } catch (error) {
        console.error(error);
      }
    },
    searchPhotos() {
    },
  },
};
</script>
