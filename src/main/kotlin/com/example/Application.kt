package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlin.collections.filter

@Serializable
data class User(val id: Int, val name: String, val age: Int)

val users = mutableListOf(
    User(1, "Alice", 25),
    User(2, "Bob", 30)
)

fun main() {
    embeddedServer(Netty, port = 8088, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get("/users") {
            val ageMin = call.request.queryParameters["ageMin"]?.toIntOrNull()
            val ageMax = call.request.queryParameters["ageMax"]?.toIntOrNull()
            val filtered = users.filter { user ->
                (ageMin == null || user.age >= ageMin)
                        && (ageMax == null || user.age <= ageMax)
            }

            call.respond(filtered)
        }

        get("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val user = users.find { it.id == id }
            if (user != null) {
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }

        post("/users") {
            val newUser = call.receive<User>()
            if (users.any { it.id == newUser.id }) {
                call.respond(HttpStatusCode.Conflict, "User with this ID already exists")
            } else {
                users.add(newUser)
                call.respond(HttpStatusCode.Created, newUser)
            }
        }

        delete("/users/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
                return@delete
            }

            val removed = users.removeIf { it.id == id }
            if (removed) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found")
            }
        }
    }
}
