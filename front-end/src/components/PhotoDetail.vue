<template>
    <div>
        <h2>Photo Details:</h2>

        <p>{{ photo.title }}</p>
        <p>{{ photo.description }}</p>
        <img :src="photo.url" alt="Photo Preview">
        <ul>
            <li v-for="category in photo.categories">{{ category.name }}</li>
        </ul>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            photo: [],
        };
    },
    mounted() {
        this.fetchPhoto();
    },
    methods: {
        async fetchPhoto() {
            try {
                const photoId = this.$route.params.id;
                console.log(photoId)
                const response = await fetch(`http://localhost:8080/api/photos/${photoId}`);
                const photo = await response.json();
                this.photo = photo;
            } catch (error) {
                console.error(error);
            }
        },
    },
};
</script>