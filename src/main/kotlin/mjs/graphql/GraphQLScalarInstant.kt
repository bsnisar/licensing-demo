package mjs.graphql

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class GraphQLScalarInstant : GraphQLScalarType(
    "Instant",
    "GraphQL scalar for instant",
    object : Coercing<Instant, String> {

        override fun serialize(dataFetcherResult: Any): String {
            if (dataFetcherResult is Instant) {
                return dataFetcherResult.toString()
            }
            throw CoercingSerializeException("Invalid value '$dataFetcherResult' instant")
        }

        override fun parseValue(input: Any): Instant {
            try {
                return Instant.parse(input.toString())
            } catch (ex: Exception) {
                throw CoercingParseValueException("Invalid value '$input' for instant")
            }
        }

        override fun parseLiteral(input: Any): Instant {
            if (input is StringValue) {
                return Instant.parse(input.value)
            }
            throw CoercingParseLiteralException("Invalid value '$input' for instant")
        }
    }
)