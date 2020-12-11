package org.acme

import io.smallrye.reactive.messaging.MessageConverter
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Message
import java.lang.reflect.Type
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

class DummyData

@ApplicationScoped
class DummyDataConverter : MessageConverter {
    override fun canConvert(`in`: Message<*>, target: Type): Boolean = true

    override fun convert(`in`: Message<*>, target: Type): Message<*> {
        println("Converting $`in`")
        return `in`.withPayload(DummyData())
    }
}

@ApplicationScoped
@Path("/hello-resteasy")
class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello RESTEasy"

    @Incoming("test")
    fun process(data: DummyData) {
        println("Processing $data")
    }
}
