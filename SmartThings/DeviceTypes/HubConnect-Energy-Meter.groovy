/*
 *	Copyright 2019 Steve White
 *
 *	Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *	use this file except in compliance with the License. You may obtain a copy
 *	of the License at:
 *
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *	WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *	License for the specific language governing permissions and limitations
 *	under the License.
 *
 *
 */
metadata
{
	definition(name: "HubConnect Energy Meter", namespace: "shackrat", author: "Steve White", ocfDeviceType: "oic.r.energy.consumption", importUrl: "https://raw.githubusercontent.com/HubitatCommunity/HubConnect/master/SmartThings/DeviceTypes/HubConnect-Energy-Meter.groovy")
	{
		capability "Energy Meter"
		capability "Refresh"

		attribute "version", "string"

		command "sync"
	}

	tiles (scale: 2)
	{
		multiAttributeTile(name: "energy", type: "lighting", width: 6, height: 4, canChangeIcon: true)
		{
			tileAttribute("energy", key: "PRIMARY_CONTROL")
			{
				attributeState "energy", label: '${currentValue} kWh'
			}
		}
		standardTile("refresh", "device.power", inactiveLabel: false, decoration: "flat", width: 2, height: 2)
		{
			state "default", label: '', action: "refresh.refresh", icon: "st.secondary.refresh"
		}
		standardTile("sync", "sync", inactiveLabel: false, decoration: "flat", width: 2, height: 2)
		{
			state "default", label: 'Sync', action: "sync", icon: "st.Bath.bath19"
		}
		valueTile("version", "version", inactiveLabel: false, decoration: "flat", width: 2, height: 2)
		{
			state "default", label: '${currentValue}'
		}

		main(["energy"])
		details(["energy", "sync", "refresh", "version"])
	}
}


/*
	installed

	Doesn't do much other than call initialize().
*/
def installed()
{
	initialize()
}


/*
	updated

	Doesn't do much other than call initialize().
*/
def updated()
{
	initialize()
}


/*
	initialize

	Doesn't do much other than call refresh().
*/
def initialize()
{
	refresh()
}


/*
	parse

	In a virtual world this should never be called.
*/
def parse(String description)
{
	log.trace "Msg: Description is $description"
}


/*
	refresh

	Refreshes the device by requesting an update from the client hub.
*/
def refresh()
{
	// The server will update status
	parent.sendDeviceEvent(device.deviceNetworkId, "refresh")

}


/*
	sync

	Synchronizes the device details with the parent.
*/
def sync()
{
	// The server will respond with updated status and details
	parent.syncDevice(device.deviceNetworkId, "power")
	sendEvent([name: "version", value: "v${driverVersion.major}.${driverVersion.minor}.${driverVersion.build}"])
}
def getDriverVersion() {[platform: "SmartThings", major: 1, minor: 6, build: 0]}
