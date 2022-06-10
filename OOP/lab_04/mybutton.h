#ifndef MYBUTTON_H
#define MYBUTTON_H

#include <QPushButton>
#include <QObject>
#include <QDebug>

#include <QObject>
#include <QDebug>

class MyButton : public QObject
{
Q_OBJECT
public:
    enum Status
    {
        ACTIVE,
        INACTIVE
    };

    explicit MyButton(const int _floor) : floor(_floor)
    {}

signals:
    void buttonPressed(int floor);
    void buttonUnpressed(int floor);

public slots:
    void slotActive();
    void slotInactive(const int floor);

private:
    int floor;
    Status status = INACTIVE;
};

#endif // MYBUTTON_H
