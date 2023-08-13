package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import ru.skypro.tgbot_petsingoodhands.entity.Shelter;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHeader;
import ru.skypro.tgbot_petsingoodhands.message.Messages;
import ru.skypro.tgbot_petsingoodhands.message.service.ShelterService;
import ru.skypro.tgbot_petsingoodhands.entity.Animal;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class StartHeaders implements TelegramHeader {

    private final Messages messages;

    private final Pattern pattern = Pattern.compile("(1)(!!)(\\d+)(!!)(\\d+)(!!)(1)");

    public StartHeaders(Messages messages) {
        this.messages = messages;
    }


    @Override
    public boolean appliesTo(Update update) {
        return Objects.nonNull(update.message()) ? pattern.matcher(update.callbackQuery().data()).find() : false;
    }

    @Override
    public void handleUpdate(Update update) {
        InlineKeyboardMarkup keyBoard = new InlineKeyboardMarkup();
        Matcher matcher = pattern.matcher(update.callbackQuery().data());
        Long shelterId = Long.parseLong(matcher.group(5));
        InlineKeyboardButton button1 = new InlineKeyboardButton("Собаки").callbackData("(1)(!!)(" + shelterId + ")(!!)(1)");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Кошки").callbackData("(1)(!!)(" + shelterId + ")(!!)(2)");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Позвать волонтера").callbackData("0");
        keyBoard.addRow(button1, button2, button3);
        messages.sendMessageWithKeyboard(update.callbackQuery().from().id(), "Выберите животное, которое хотели бы приютить", keyBoard);

    }
}
