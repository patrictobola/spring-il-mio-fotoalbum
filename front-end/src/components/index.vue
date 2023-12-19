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

    <div>
      <form @submit.prevent="sendMessage">
        <label for="email">Email:</label>
        <input type="email" v-model="mailData.email" required>

        <label for="message">Message:</label>
        <textarea v-model="mailData.message" required></textarea>

        <button type="submit">Invia</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      photos: [],
      searchQuery: '',
      mailData: {
        email: '',
        message: ''
      }
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
    async sendMessage() {
      try {
        const response = await fetch('http://localhost:8080/api/messages', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.mailData)
        });
        this.mailData = {
          email: '',
          message: ''
        }
      } catch (error) {
        console.error('Errore durante l\'invio del messaggio', error);
      }
    }
  },
};
</script>
