package org.acme.kafka.processor;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.acme.kafka.model.Quote;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Blocking;
import java.util.Random;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;

import org.acme.kafka.model.Quote;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
/**
 * A bean consuming data from the "quote-requests" Kafka topic (mapped to "requests" channel) and giving out a random quote.
 * The result is pushed to the "quotes" Kafka topic.
 */
@ApplicationScoped

@Path("/")
public class QuotesProcessor {


    private Logger processorLog = Logger.getLogger(QuotesProcessor.class.getName());
    private Random random = new Random();
    private int counter = 1;
    private Quote newQuote = new Quote();

    @Incoming("requests") // Indicates that the method consumes the items from the requests channel.
    @Outgoing("quotes") // Indicates that the objects returned by the method are sent to the quotes channel.
    @Blocking // Indicates that the processing is blocking and cannot be run on the caller thread.

    public Quote process(String quoteRequest) throws InterruptedException {
        // simulate some hard working task
        //System.out.println("in quote processor");

        Thread.sleep(200);
       newQuote = new Quote(quoteRequest, random.nextInt(100));
        processorLog.info("This is the final quote "+ newQuote + "Quote Number:"+counter++);
       return newQuote;
    }
}