const { ApolloClient, HttpLink, ApolloLink, InMemoryCache, concat, gql } = require('@apollo/client');

const httpLink = new HttpLink({ uri: 'http://localhost:8080/graphql' });

const authMiddleware = new ApolloLink((operation, forward) => {
  operation.setContext({
    headers: {
      authorization: localStorage.getItem('token') || null,
    }
  });

  return forward(operation);
})

const client = new ApolloClient({
  cache: new InMemoryCache(),
  link: concat(authMiddleware, httpLink),
});

client
  .query({
    query: gql`
      query GetNumbers {
        numbers
      }
    `
  })
  .then(result => console.log(result));
