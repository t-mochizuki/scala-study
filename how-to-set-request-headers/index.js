const { ApolloClient, InMemoryCache, gql } = require('@apollo/client');

const client = new ApolloClient({
  uri: 'http://localhost:8080/graphql',
  cache: new InMemoryCache()
});

client
  .query({
    query: gql`
      query GetSchemaTypes {
        __schema {
          types {
            name
          }
        }
      }
    `
  })
  .then(result => console.log(result));
