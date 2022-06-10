#ifndef DOORS_H
#define DOORS_H


#include <QObject>
#include <QTimer>
#include "config.h"


class Doors : public QObject
{
    friend class Cabin;

Q_OBJECT
public:
    enum Status
    {
        CLOSED,
        OPEN,
        OPENING,
        CLOSING
    };

    Doors();

signals:
    void doorsOpened();
    void doorsClosed();

public slots:
    void slotOpen();
    void slotClose();

private slots:
    void slotOpening();
    void slotClosing();


private:
    QTimer openingTimer, closingTimer, waitTimer;
    Status status = CLOSED;
};

#endif // DOORS_H
