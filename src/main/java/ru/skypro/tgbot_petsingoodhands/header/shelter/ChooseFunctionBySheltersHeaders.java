package ru.skypro.tgbot_petsingoodhands.header.shelter;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import ru.skypro.tgbot_petsingoodhands.header.TelegramHandler;
import ru.skypro.tgbot_petsingoodhands.message.Messages;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChooseFunctionBySheltersHeaders implements TelegramHandler {
    private final Messages messages;
    private final Pattern pattern = Pattern.compile("(1)(!!)(\\d+)(!!)(\\d+)(!!)(1)");

    public ChooseFunctionBySheltersHeaders(Messages messages) {
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
        InlineKeyboardButton button1 = new InlineKeyboardButton("Узнать о приюте").callbackData("(1)(!!)(" + shelterId + ")(!!)(1)");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Узнать расписание работы приюта и адрес, схему проезда").callbackData("(1)(!!)(" + shelterId + ")(!!)(2)");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Узнать контактные данные охраны для оформления пропуска на машину.").callbackData("(1)(!!)(" + shelterId + ")(!!)(3)");
        InlineKeyboardButton button4 = new InlineKeyboardButton("Узнать общие рекомендации о технике безопасности на территории приюта.").callbackData("(1)(!!)(" + shelterId + ")(!!)(4)");
        InlineKeyboardButton button5 = new InlineKeyboardButton("Принять и записать контактные данные для связи.").callbackData("(1)(!!)(" + shelterId + ")(!!)(5)");
        InlineKeyboardButton button6 = new InlineKeyboardButton("Узнать о приюте").callbackData("0");
        keyBoard.addRow(button1, button2, button3, button4, button5, button6);
        messages.sendMessageWithKeyboard(update.callbackQuery().from().id(), "Выберете пункт который Вас интересует", keyBoard);


    }
}
