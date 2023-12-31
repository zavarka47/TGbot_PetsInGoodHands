package ru.skypro.tgbot_petsingoodhands.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.repository.AnimalRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Handler;

@Component
public class UpdateListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final List<TelegramHeader> handlers;
    private final Messages messages;
    private Logger logger = LoggerFactory.getLogger(UpdateListener.class);

    public UpdateListener(TelegramBot telegramBot, List<TelegramHeader> handlers, Messages messages) {
        this.telegramBot = telegramBot;
        this.handlers = handlers;
        this.messages = messages;
    }
    @PostConstruct
    public void init (){
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try{
            updates.forEach( update -> {
                logger.info("Handlers update: {}", update);
                Long chatId = null;
                if (Objects.nonNull(update.message())){
                    chatId = update.message().chat().id();
                }
                if ( Objects.nonNull(update.callbackQuery())){
                    chatId = update.callbackQuery().from().id();
                }

                boolean messageHandler = false;
                for (TelegramHeader handler : handlers) {
                    if (handler.appliesTo(update)){
                        handler.handleUpdate(update);
                    }
                    messageHandler = true;
                }
                if (!messageHandler){
                    messages.sendSimpleMessage(chatId, "Такой команды я не знаю :(");
                }
            });
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
