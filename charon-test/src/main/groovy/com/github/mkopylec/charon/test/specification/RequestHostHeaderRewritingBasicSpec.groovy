package com.github.mkopylec.charon.test.specification

import static com.github.mkopylec.charon.test.assertions.Assertions.assertThat
import static com.github.mkopylec.charon.test.assertions.Assertions.assertThatServers
import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

abstract class RequestHostHeaderRewritingBasicSpec extends BasicSpec {

    def "Should rewrite request 'Host' header when proper interceptor is added"() {
        when:
        def response = sendRequest(GET, '/request/host/header', ['Host': 'example.com'])

        then:
        assertThat(response)
                .hasStatus(OK)
                .hasNoBody()
        assertThatServers(localhost8080)
                .haveReceivedRequest(GET, '/request/host/header', ['Host': 'localhost:8080'])
    }

    def "Should not rewrite request 'Host' header by default"() {
        when:
        def response = sendRequest(GET, '/default', ['Host': 'example.com'])

        then:
        assertThat(response)
                .hasStatus(OK)
                .hasNoBody()
        assertThatServers(localhost8080, localhost8081)
                .haveReceivedRequest(GET, '/default', ['Host': 'example.com'])
    }
}