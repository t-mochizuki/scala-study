const { ApolloClient, InMemoryCache, gql } = require('@apollo/client');

const client = new ApolloClient({
  uri: 'http://localhost:8080/graphql',
  cache: new InMemoryCache()
});

const token = localStorage.getItem('token');

client
  .query({
    query: gql`
      query GetNumbers {
        numbers
      }
    `,
    context: {
      headers: {
        authorization: token
      }
    }
  })
  .then(result => console.log(result));
