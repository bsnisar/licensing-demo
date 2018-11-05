package mjs.graphql

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.lang.IllegalArgumentException
import java.util.UUID

object UUIDScalar {

    val uuid = GraphQLScalarType("uuid", "A custom scalar for UUID", object : Coercing<UUID, String> {

        override fun serialize(dataFetcherResult: Any): String {
            try {
                val possibleUUID = dataFetcherResult.toString()
                UUID.fromString(possibleUUID)
                return possibleUUID
            } catch (ex: IllegalArgumentException) {
                throw CoercingSerializeException(ex.message)
            }
        }

        override fun parseValue(input: Any): UUID {
            try {
                return UUID.fromString(input.toString())
            } catch (ex: IllegalArgumentException) {
                throw CoercingParseValueException("Unable to parse $input as a UUID")
            }
        }

        override fun parseLiteral(input: Any): UUID {
            if (input is StringValue) {
                try {
                    return UUID.fromString(input.value)
                } catch (ex: IllegalArgumentException) {
                    throw CoercingParseLiteralException("Value $input is not a UUID")
                }
            }
            throw CoercingParseLiteralException("Value $input is not a UUID")
        }
    })
}