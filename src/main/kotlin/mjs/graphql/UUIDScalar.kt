package mjs.graphql

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UUIDScalar : GraphQLScalarType(
    "UUID",
    "GraphQL scalar for UUID",
    object : Coercing<UUID, String> {

        override fun serialize(dataFetcherResult: Any): String {
            if (dataFetcherResult is UUID) {
                return dataFetcherResult.toString()
            }
            throw CoercingSerializeException("Invalid value '$dataFetcherResult' for UUID")
        }

        override fun parseValue(input: Any): UUID {
            try {
                return UUID.fromString(input.toString())
            } catch (ex: IllegalArgumentException) {
                throw CoercingParseValueException("Invalid value '$input' for UUID")
            }
        }

        override fun parseLiteral(input: Any): UUID {
            if (input is StringValue) {
                return UUID.fromString(input.value)
            }
            throw CoercingParseLiteralException("Invalid value '$input' for UUID")
        }
    }
)