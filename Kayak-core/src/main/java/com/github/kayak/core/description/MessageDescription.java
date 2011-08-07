/**
 * 	This file is part of Kayak.
 *
 *	Kayak is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Kayak is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU Lesser General Public License
 *	along with Kayak.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.github.kayak.core.description;

import com.github.kayak.core.Frame;
import java.util.HashSet;

/**
 *
 * @author Jan-Niklas Meier < dschanoeh@googlemail.com >
 */
public class MessageDescription {

    private int id;
    private int interval;
    private String name;
    private Node producer;
    private HashSet<SignalDescription> signals;
    private BusDescription bus;

    public BusDescription getBus() {
        return bus;
    }

    public Node getProducer() {
        return producer;
    }

    public void setProducer(Node producer) {
        this.producer = producer;
    }

    public int getId() {
        return id;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<SignalDescription> getSignals() {
        return signals;
    }

    public void setSignals(HashSet<SignalDescription> signals) {
        this.signals = signals;
    }

    protected MessageDescription(BusDescription bus, int id) {
        signals = new HashSet<SignalDescription>();
        this.bus = bus;
        this.id = id;
    }

    public Message decodeFrame(Frame f) {

        Message m = new Message();
        m.setId(id);
        m.setInterval(interval);
        m.setName(name);
        m.setProducer(producer.getName());
        HashSet<Signal> ret = m.getSignals();

        for(SignalDescription s : signals) {
            Signal signal = s.decodeData(f.getData());
            if(s != null)
                ret.add(signal);
        }
        
        return m;
    }

    public SignalDescription createSignal() {
        SignalDescription s = new SignalDescription(this);
        signals.add(s);
        return s;
    }

}
