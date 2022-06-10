#include "doors.hpp"

Doors::Doors()
{
    this->openingTimer.setSingleShot(true);
    this->openingTimer.setInterval(DOORS_OPEN_TIME);
    this->waitTimer.setSingleShot(true);
    this->waitTimer.setInterval(DOORS_WAIT_TIME);
    this->closingTimer.setSingleShot(true);
    this->closingTimer.setInterval(DOORS_CLOSE_TIME);
    connect(&this->openingTimer, &QTimer::timeout, this, &Doors::slotOpen);
    connect(&this->waitTimer, &QTimer::timeout, this, &Doors::slotClosing);
    connect(&this->closingTimer, &QTimer::timeout, this, &Doors::slotClose);
}

void Doors::slotOpening()
{
    if (this->status == CLOSED)
    {
        this->status = OPENING;
        this->openingTimer.start();
        qDebug("Двери лифта начали открываться.");
    }
}

void Doors::slotOpen()
{
    if (this->status == OPENING)
    {
        this->status = OPEN;
        this->waitTimer.start();
        qDebug("Двери лифта открылись.");
        emit doorsOpened();
    }
}

void Doors::slotClosing()
{
    if (this->status == OPEN)
    {
        this->status = CLOSING;
        this->closingTimer.start();
        qDebug("Двери лифта начали закрываться.");
    }
}

void Doors::slotClose()
{
    if (this->status == CLOSING)
    {
        this->status = CLOSED;
        qDebug("Двери лифта закрылись.");
        emit doorsClosed();
    }
}
